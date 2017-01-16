package com.tesco.bootcamp.orderstatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by cx11 on 11/01/2017.
 */

@Service
public class OrderService {


    private final OrderSystemCheck orderSystemCheck;
    private final DeliverySystemCaller deliverySystemCaller;

    @Autowired
    public OrderService(OrderSystemCheck orderSystemCheck, DeliverySystemCaller deliverySystemCaller) {
        this.orderSystemCheck = orderSystemCheck;
        this.deliverySystemCaller = deliverySystemCaller;

    }

    public Optional<OrderStatus> getOrderStatus(String orderid) {
        //Using the order ID to send to the delivery system class, which will in return get the latest order event for
        //the order

        if (orderSystemCheck.isOrderMissing(orderid)) {
            return empty();
        }

        Optional<TrackingEvent> latestTrackingEvent = deliverySystemCaller.getLatestTrackingEvent(orderid);
        String orderStatus = latestTrackingEvent
                .map(TrackingEvent::getEventType)
                .map(EventType::eventToOrderStatus)
                .orElse("ORDER_PLACED");

        return of(new OrderStatus(orderid, orderStatus));
    }

}
