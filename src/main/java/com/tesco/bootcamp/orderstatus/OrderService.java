package com.tesco.bootcamp.orderstatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by cx11 on 11/01/2017.
 */

@Service
public class OrderService {


//private final String orderid;
//     @Autowired
//
//    public OrderService(String orderid){
//        this.orderid = orderid;
//
//    }

    public OrderStatus  getOrderStatus(String orderid) {
        //Using the order ID to send to the delivery system class, which will in return get the latest order event for
        //the order

        DelSystemCaller delSystemCaller = new DelSystemCaller();
        TrackingEvent eventRes = delSystemCaller.getLatestTrackingEvent(orderid);

        String latestEvent = eventRes.getEventType();


        String orderStatus = eventToOrderStatus(latestEvent);
        OrderStatus os = new OrderStatus(orderid, orderStatus);
        return os;


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

//    public Optional<OrderStatus> getOrderStatus(String orderId) {
//        return Optional.empty();
//
//    }
    }
}
