package com.tesco.bootcamp.orderstatus;

/**
 * Created by MikeSamsung7 on 11/01/2017.
 */
public class TrackingEvent {

    private String eventType;
    private String vanID;
    private String parcelID;
    private String eventDateTime;

    public String getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(String eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getParcelID() {
        return parcelID;
    }

    public void setParcelID(String parcelID) {
        this.parcelID = parcelID;
    }

    public String getVanID() {
        return vanID;
    }

    public void setVanID(String vanID) {
        this.vanID = vanID;
    }

    public String getEventType() {
        return "ORDER_PICKED";
    }
        //eventType;


    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
