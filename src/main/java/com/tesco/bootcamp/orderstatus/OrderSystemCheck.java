package com.tesco.bootcamp.orderstatus;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Bradley on 12/01/2017.
 */
public class OrderSystemCheck {


    private String orderServiceBaseURL;
    private String checkStatusByOrder;


    public OrderSystemCheck() {

        orderServiceBaseURL = "http://orders.dev-environment.tesco.codurance.io:8080/";
        checkStatusByOrder = "/ghs/order?orderId=";

    }




    public boolean CheckOrder(String orderId) {
        boolean checkEvents = checkOrders(orderId);



//        for (EventFromOrderService event : checkEvents) {
//            status = event.getStatus();
//        }
//            if (status == 404)
//                result = false;


        return checkEvents;
    }



    private boolean checkOrders(String orderId){

        RestTemplate restTemplate = new RestTemplate();
        StringBuilder sb = new StringBuilder();
        sb.append(orderServiceBaseURL);
        sb.append(checkStatusByOrder);
        sb.append(orderId);

        try {
            ResponseEntity<List<EventFromOrderService>> collectRequestResult = restTemplate.exchange(
                    sb.toString(),
                    HttpMethod.GET,null,
                    new ParameterizedTypeReference<List<EventFromOrderService>>(){});

            return collectRequestResult.getStatusCode().equals(HttpStatus.OK);

//            return collectRequestResult.getBody().stream()
//                    .map(eventFromOrderService -> new EventFromOrderService(eventFromOrderService.getTimestamp(),
//                            eventFromOrderService.getStatus(),
//                            eventFromOrderService.getError(),
//                            eventFromOrderService.getException(),
//                            eventFromOrderService.getMessage(),
//                            eventFromOrderService.getPath())).collect(Collectors.toList());

        } catch (Exception e) {


            throw new RuntimeException("Failed to obtain parcel ID", e);

        }





    }

}
