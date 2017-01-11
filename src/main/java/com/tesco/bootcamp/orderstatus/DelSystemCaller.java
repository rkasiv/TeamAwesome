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
        deliveryServiceBaseURL = "http://delivery.dev-environment.tesco.codurance.io:8080";
        getEventsByOrderURL = "events/ghs/order";
        getEventsByParcelURL = "events/ghs/parcel";
    }

    public TrackingEvent getLatestOrderEvent(String orderID){



        return new TrackingEvent();
    }

    private String getParcelID(String orderID){



        return "";
    }



    public void setParcelID(String parcelID) {
        this.parcelID = parcelID;
    }

    public List<String> collectParcelID(String orderID) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<EventFromDelService>> collectRequestResult = restTemplate.exchange(
                    "http://delivery.dev-environment.tesco.codurance.io:8080/events/ghs/order?orderId=a3712165-9a9a-4726-aeb6-e263f80635c0",HttpMethod.GET,null,  new ParameterizedTypeReference<List<EventFromDelService>>() {});


            return null;
        } catch (Exception e) {

            throw new RuntimeException("Parcels cannot be collected from the shop", e);
        }
    }
}
