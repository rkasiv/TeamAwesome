package com.tesco.bootcamp.orderstatus;

import static java.util.Arrays.stream;

public enum EventType {
    ORDER_PICKED("Ready_For_Delivery"),
    PARCELS_DISPATCHED("In_Delivery"),
    PARCEL_ATTEMPTING_DELIVERY("In_Delivery"),
    PARCEL_DELIVERED("Delivered"),
    FAILED_TO_DELIVER("Returning_to_shop"),
    RETURNED_TO_SHOP("Ready_For_delivery");

    private final String orderStatus;

    EventType(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public static boolean isKnownEvent(TrackingEvent event) {
        return stream(EventType.values())
                .anyMatch(eventType -> eventType.equalsEventType(event));
    }

    private boolean equalsEventType(TrackingEvent event) {
        return this.name().equals(event.getEventType());
    }

    public static String eventToOrderStatus(String eventTypeAsString) {
        EventType eventType = EventType.valueOf(eventTypeAsString);
        return eventType.orderStatus;
    }
}
