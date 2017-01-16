package com.tesco.bootcamp.orderstatus;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Bradley on 12/01/2017.
 */
@Service
public class OrderSystemCheck {


    private String orderServiceBaseURL;
    private String checkStatusByOrder;


    public OrderSystemCheck() {

        orderServiceBaseURL = "http://orders.dev-environment.tesco.codurance.io:8080/";
        checkStatusByOrder = "ghs/order?orderId=";
    }

    public boolean isOrderMissing(String orderId) {
        return !isOrderAvailable(orderId);
    }


    private boolean isOrderAvailable(String orderId) {

        RestTemplate restTemplate = new RestTemplate();
        StringBuilder sb = new StringBuilder();
        sb.append(orderServiceBaseURL);
        sb.append(checkStatusByOrder);
        sb.append(orderId);

        try {
            ResponseEntity<String> collectRequestResult = restTemplate.exchange(
                    sb.toString(),
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<String>() {
                    });

            return collectRequestResult.getStatusCode().equals(HttpStatus.OK);

        } catch (Exception e) {

            return false;

//            throw new RuntimeException("Failed to obtain order ID", e);
// }
        }
    }
}


// keeping this info commented out in case we need this for further
//            return collectRequestResult.getBody().stream()
//                    .map(eventFromOrderService -> new EventFromOrderService(eventFromOrderService.getTimestamp(),
//                            eventFromOrderService.getStatus(),
//                            eventFromOrderService.getError(),
//                            eventFromOrderService.getException(),
//                            eventFromOrderService.getMessage(),
//                            eventFromOrderService.getPath())).collect(Collectors.toList());