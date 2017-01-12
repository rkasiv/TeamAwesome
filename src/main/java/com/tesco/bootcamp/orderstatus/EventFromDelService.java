package com.tesco.bootcamp.orderstatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MikeSamsung7 on 11/01/2017.
 */
public class EventFromDelService {

    private String eventType;
    private String shopID;
    private String orderID;
    private String parcelID;
    private String eventDateTime;

    @JsonCreator
    public EventFromDelService(
            @JsonProperty("eventType") String eventType,
            @JsonProperty("shopId") String shopID,
            @JsonProperty("orderId") String orderID,
            @JsonProperty("parcelId") String parcelID,
            @JsonProperty("eventDateTime") String eventDateTime)
    {
        this.eventType = eventType;
        this.shopID = shopID;
        this.orderID = orderID;
        this.parcelID = parcelID;
        this.eventDateTime = eventDateTime;
    }

    public String getEventType() {
        return eventType;
    }

    public String getShopID() {
        return shopID;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getParcelID() {
        return parcelID;
    }

    public String getEventDateTime() {
        return eventDateTime;
    }
}
