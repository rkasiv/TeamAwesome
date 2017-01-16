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
                .map(OrderService::eventToOrderStatus)
                .orElse("ORDER_PLACED");

        return of(new OrderStatus(orderid, orderStatus));
    }

    public static String eventToOrderStatus(String v) {
        //Method to change event taken from the delivery system and then changing that into a delivery status for the
        //customer which will be shown to the customer
        switch (v) {
            case "ORDER_PICKED":
                return "Ready_For_Delivery";
            case "PARCELS_DISPATCHED":
                return "In_Delivery";
            case "PARCEL_ATTEMPTING_DELIVERY":
                return "In_Delivery";
            case "PARCEL_DELIVERED":
                return "Delivered";
            case "Failed_to_Deliver":
                return "Returning_to_shop";
            case "Returned_to_shop":
                return "Ready_For_delivery";
            default:
                return "Order_placed";
        }


    }
}
