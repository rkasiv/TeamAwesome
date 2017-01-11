package com.tesco.bootcamp.orderstatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by cx11 on 11/01/2017.
 */

@Service
public class OrderService {

    private final String orderid;
    private TrackingEvent trackingEvent;


    @Autowired

    public OrderService(String orderid){
        this.orderid = orderid;

    }
// test commit

    public String getOrderStatus()
    {
       // DelSystemCaller delSystemCaller;
       //TrackingEvent eventRes = delSystemCaller.getLatestOrderEvent(String orderid);

           String latestEvent =  trackingEvent.eventtype;
       // String latestevent = eventRes.status;

        String orderStatus = eventToOrderStatus(latestEvent);
        return  orderStatus;



    }

    public static String eventToOrderStatus (String v){
        switch (v){
            case "ORDER_PICKED" : return "Ready_For_Delivery";
            case "PARCELS_DISPATCHED" : return "In_Delivery";
            case "PARCEL_ATTEMPTING_DELIVERY": return "In_Delivery";
            case "PARCEL_DELIVERED" :  return "Delivered";
            case "Failed_to_Deliver" : return "Returning_to_shop";
            case "Returned_to_shop" : return "Ready_to_delivery";
            default: return "Order_placed";
        }
    }
}
