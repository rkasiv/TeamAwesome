package com.tesco.bootcamp.orderstatus;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by MikeSamsung7 on 11/01/2017.
 */
public class DelSystemCaller {

    private String parcelID;
    private String deliveryServiceBaseURL;
    private String getEventsByOrderURL;
    private String getEventsByParcelURL;
    private Object EventFromDelService;

    public DelSystemCaller(){
        deliveryServiceBaseURL = "http://delivery.dev-environment.tesco.codurance.io:8080/";
        getEventsByOrderURL = "events/ghs/order?orderId=";
        getEventsByParcelURL = "events/ghs/parcel?parcelId=";
    }

    private String getParcelID(String orderID){

        return "";
    }



    public void setParcelID(String parcelID) {
        this.parcelID = parcelID;
    }

    public List<EventFromDelService> collectParcelID(String orderID) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder sb = new StringBuilder();
        sb.append(deliveryServiceBaseURL);
        sb.append(getEventsByOrderURL);
        sb.append(orderID);
        try {
            ResponseEntity<List<EventFromDelService>> collectRequestResult = restTemplate.exchange(
                    sb.toString(),
                    HttpMethod.GET,null,
                    new ParameterizedTypeReference<List<EventFromDelService>>(){});

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

    public List<TrackingEvent> collectTrackingEvents(String parcelID) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder sb = new StringBuilder();
        sb.append(deliveryServiceBaseURL);
        sb.append(getEventsByParcelURL);
        sb.append(parcelID);
        try {
            ResponseEntity<List<TrackingEvent>> collectRequestResult = restTemplate.exchange(
                    sb.toString(),
                    HttpMethod.GET,null,
                    new ParameterizedTypeReference<List<TrackingEvent>>(){});

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
