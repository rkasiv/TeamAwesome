package com.tesco.bootcamp.orderstatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderSystemCheck {
    private final String checkStatusByOrder;
    private final String orderServiceBaseURL;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OrderSystemCheck(@Value("${order.service.base.url}") String orderServiceBaseURL) {
        this.orderServiceBaseURL = orderServiceBaseURL;
        logger.info("Order Service Base URL is " + orderServiceBaseURL);
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
        }
    }
}
