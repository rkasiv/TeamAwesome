package com.tesco.bootcamp.orderstatus;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by cx11 on 12/01/2017.
 */
public class TestOrderstatus {

    public static final String ORDER_ID = "a3712165-9a9a-4726-aeb6-e263f80635c0";

    @Test
    public void TestGetOrderStatus() {
        // Given
        OrderService ordServ = new OrderService(new OrderSystemCheck(){
            @Override
            public boolean isOrderMissing(String orderId) {
                return false;
            }
        }, new DeliverySystemCaller());
        String expected= "Picked";
        // when
        Optional<OrderStatus> actual = ordServ.getOrderStatus("a3712165-9a9a-4726-aeb6-e263f80635c0");
        // Then
        assertThat(actual.get().getStatus(),is(expected));

    }

    @Test
    public void TestGetOrderStatusWMockito() {
        // Given
        DeliverySystemCaller deliverySystemCaller = mock(DeliverySystemCaller.class);
        OrderSystemCheck orderSystemCheck = mock(OrderSystemCheck.class);
        when(orderSystemCheck.isOrderMissing(ORDER_ID))
                .thenReturn(false);
        OrderService ordServ = new OrderService(orderSystemCheck,deliverySystemCaller);
        String expected= "Picked";
        // when
        Optional<OrderStatus> actual = ordServ.getOrderStatus(ORDER_ID);
        // Then
        assertThat(actual.get().getStatus(),is(expected));

        verify(deliverySystemCaller).getLatestTrackingEvent(ORDER_ID);

    }
}

