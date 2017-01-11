package com.tesco.bootcamp.orderstatus;

public class OrderServiceStub {

    public OrderStatus getOrderStatus(String orderId){
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setStatus("DELIVERED");
        return orderStatus;
    }
}
