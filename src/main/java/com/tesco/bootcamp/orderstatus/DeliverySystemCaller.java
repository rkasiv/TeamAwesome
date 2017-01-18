package com.tesco.bootcamp.orderstatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;

@Service
public class DeliverySystemCaller {
    //private final String deliveryServiceBaseURL;
    private final String getEventsByOrderURL;
    private final String getEventsByParcelURL;
    private final RestTemplate restTemplate;
    private final String deliveryServiceBaseURL;


    public DeliverySystemCaller(RestTemplate restTemplate, @Value("${delivery.service.base.url}") String deliveryServiceBaseURL) {
        this.restTemplate = restTemplate;
        this.deliveryServiceBaseURL = deliveryServiceBaseURL;
        getEventsByOrderURL = "events/ghs/order?orderId=";
        getEventsByParcelURL = "events/ghs/parcel?parcelId=";
    }

    public Optional<TrackingEvent> getLatestTrackingEvent(String orderID) {
        List<EventFromDelService> orderEvents = collectParcelID(orderID);

        if (orderEvents.size() <= 0) {
            return empty();
        }

        List<TrackingEvent> trackingEvents = collectTrackingEvents(getLastParcelId(orderEvents));

        return returnLatestTrackingEvent(trackingEvents);
    }

    private String getLastParcelId(List<EventFromDelService> orderEvents) {
        return orderEvents.get(orderEvents.size() - 1).getParcelID();
    }

    private Optional<TrackingEvent> returnLatestTrackingEvent(List<TrackingEvent> trackingEvents) {
        return trackingEvents.stream()
                .filter(EventType::isKnownEvent)
                .sorted(DeliverySystemCaller.this::lastEventFirst)
                .findFirst();
    }

    private int lastEventFirst(TrackingEvent event1, TrackingEvent event2) {
        Date firstDate = parseDate(event1);
        Date secondDate = parseDate(event2);

        return secondDate.compareTo(firstDate);
    }

    private Date parseDate(TrackingEvent event) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                    .parse(event.getEventDateTime());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse earliestDate");
        }
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
            throw new RuntimeException("Failed to obtain tracking events", e);
        }
    }
}
