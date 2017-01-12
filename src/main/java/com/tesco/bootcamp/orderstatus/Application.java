package com.tesco.bootcamp.orderstatus;

/**
 * Created by cx11 on 11/01/2017.
 */
public class Application {

    public static void main(String[] args) {

        OrderService orderService = new OrderService("a3712165-9a9a-4726-aeb6-e263f80635c0");
        String test = orderService.getOrderStatus();

        System.out.println(test);
        System.out.println();


    }


}
