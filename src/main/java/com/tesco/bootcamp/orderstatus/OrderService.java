package com.tesco.bootcamp.orderstatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by cx11 on 11/01/2017.
 */

@Component
public class OrderService {

    public Optional<OrderStatus> getOrderStatus(String orderId) {
        return Optional.empty();
    }
}
