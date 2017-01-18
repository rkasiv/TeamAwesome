package com.tesco.bootcamp.orderstatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderStatusServiceTest {

    public static final String ORDER_ID = "SOME ORDER ID3";
    public static final TrackingEvent ORDER_PICKED_EVENT = new TrackingEvent("ORDER_PICKED", "VAN_ID", "PARCEL_ID", "Some date");
    private final String DELIVERY_URL = "URL TO DELIVERY SYSTEM";

    @Mock
    private OrderSystemCheck orderSystemCheck;

    @Mock
    private DeliverySystemCaller deliverySystemCaller;

    @Test
    public void do_not_return_any_status_if_order_does_not_exists() {
        // given
        RestTemplate restTemplate = new RestTemplate();
        when(orderSystemCheck.isOrderMissing(ORDER_ID)).thenReturn(true);
        OrderService orderService = new OrderService(orderSystemCheck, new DeliverySystemCaller(restTemplate, DELIVERY_URL));
        // when
        Optional<OrderStatus> orderStatusForNonExistingOrder = orderService.getOrderStatus(ORDER_ID);
        // Then
        assertThat(orderStatusForNonExistingOrder.isPresent(), is(false));
    }

    @Test
    public void return_ready_for_delivery_status_when_the_last_event_was_order_picked() {
        // Given
        when(orderSystemCheck.isOrderMissing(ORDER_ID)).thenReturn(false);
        when(deliverySystemCaller.getLatestTrackingEvent(ORDER_ID)).thenReturn(Optional.of(ORDER_PICKED_EVENT));
        OrderService orderService = new OrderService(orderSystemCheck, deliverySystemCaller);
        String expected = "Ready_For_Delivery";
        // when
        Optional<OrderStatus> orderStatusWhenOrderWasJustPicked = orderService.getOrderStatus(ORDER_ID);
        // Then
        assertThat(orderStatusWhenOrderWasJustPicked.get().getStatus(), is(expected));
        verify(deliverySystemCaller).getLatestTrackingEvent(ORDER_ID);
    }
}

