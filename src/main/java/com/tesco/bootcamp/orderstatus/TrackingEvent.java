package com.tesco.bootcamp.orderstatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MikeSamsung7 on 11/01/2017.
 */
public class TrackingEvent {

    private String eventType;
    private String vanID;
    private String parcelID;
    private String eventDateTime;

    @JsonCreator
    public TrackingEvent(
            @JsonProperty("eventType") String eventType,
            @JsonProperty("vanId") String vanID,
            @JsonProperty("parcelId") String parcelID,
            @JsonProperty("eventDateTime") String eventDateTime)
    {
        this.eventType = eventType;
        this.vanID = vanID;
        this.parcelID = parcelID;
        this.eventDateTime = eventDateTime;
    }


    public String getEventType() {
        return eventType;
    }

    public String getVanID() {
        return vanID;
    }

    public String getParcelID() {
        return parcelID;
    }

    public String getEventDateTime() {
        return eventDateTime;
    }
}
