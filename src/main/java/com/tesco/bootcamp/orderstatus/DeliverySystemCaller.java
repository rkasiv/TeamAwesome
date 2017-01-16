package com.tesco.bootcamp.orderstatus;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliverySystemCaller {
    private final String deliveryServiceBaseURL;
    private final String getEventsByOrderURL;
    private final String getEventsByParcelURL;
    private final RestTemplate restTemplate;

    public DeliverySystemCaller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        deliveryServiceBaseURL = "http://delivery.dev-environment.tesco.codurance.io:8080/";
        getEventsByOrderURL = "events/ghs/order?orderId=";
        getEventsByParcelURL = "events/ghs/parcel?parcelId=";
    }

    public DeliverySystemCaller() {
        restTemplate = new RestTemplate();
        deliveryServiceBaseURL = "http://delivery.dev-environment.tesco.codurance.io:8080/";
        getEventsByOrderURL = "events/ghs/order?orderId=";
        getEventsByParcelURL = "events/ghs/parcel?parcelId=";
    }


    public TrackingEvent getLatestTrackingEvent(String orderID) {
        List<EventFromDelService> orderEvents = collectParcelID(orderID);

        if (orderEvents.size() <= 0) {
            return new TrackingEvent("NO_EVENT", "", "", "");
        }


        List<TrackingEvent> trackingEvents = collectTrackingEvents(getLastParcelId(orderEvents));

        if (trackingEvents.size() == 1) {
            return new TrackingEvent("NO_EVENT", "", "", "");
        }

        return returnLatestTrackingEvent(trackingEvents);
    }

    private String getLastParcelId(List<EventFromDelService> orderEvents) {
        return orderEvents.get(orderEvents.size() - 1).getParcelID();
    }

    private TrackingEvent returnLatestTrackingEvent(List<TrackingEvent> trackingEvents) {
        TrackingEvent latestEvent;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date earliestDate;
        try {
            earliestDate = df.parse(trackingEvents.get(0).getEventDateTime());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse earliestDate");
        }
        latestEvent = trackingEvents.get(0);
        Date eventDate;

        for (TrackingEvent event : trackingEvents) {
            try {
                eventDate = df.parse(event.getEventDateTime());
            } catch (Exception e) {

                throw new RuntimeException("Failed to parse eventDate", e);
            }

            if (eventDate.after(earliestDate)) {
                latestEvent = event;
            }
        }

        return latestEvent;
    }


    private List<EventFromDelService> collectParcelID(String orderID) {
        StringBuilder sb = new StringBuilder();
        sb.append(deliveryServiceBaseURL);
        sb.append(getEventsByOrderURL);
        sb.append(orderID);
        try {
            ResponseEntity<List<EventFromDelService>> collectRequestResult = restTemplate.exchange(
                    sb.toString(),
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<EventFromDelService>>() {
                    });

            return collectRequestResult.getBody().stream()
                    .map(eventFromDelService -> new EventFromDelService(eventFromDelService.getEventType(),
                            eventFromDelService.getShopID(),
                            eventFromDelService.getOrderID(),
                            eventFromDelService.getParcelID(),
                            eventFromDelService.getEventDateTime())).collect(Collectors.toList());

        } catch (Exception e) {

            throw new RuntimeException("Failed to obtain parcel ID", e);
        }
    }

    private List<TrackingEvent> collectTrackingEvents(String parcelID) {
        StringBuilder sb = new StringBuilder();
        sb.append(deliveryServiceBaseURL);
        sb.append(getEventsByParcelURL);
        sb.append(parcelID);
        try {
            ResponseEntity<List<TrackingEvent>> collectRequestResult = restTemplate.exchange(
                    sb.toString(),
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<TrackingEvent>>() {
                    });

            return collectRequestResult.getBody().stream()
                    .map(trackingEvent -> new TrackingEvent(trackingEvent.getEventType(),
                            trackingEvent.getVanID(),
                            trackingEvent.getParcelID(),
                            trackingEvent.getEventDateTime())).collect(Collectors.toList());


        } catch (Exception e) {

            throw new RuntimeException("Failed to obtain tracking ID", e);
        }
    }
}
