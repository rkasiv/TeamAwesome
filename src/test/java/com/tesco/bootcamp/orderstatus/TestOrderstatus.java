package com.tesco.bootcamp.orderstatus;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by cx11 on 12/01/2017.
 */
public class TestOrderstatus {

    @Test
    public void TestGetOrderStatus() {
        // Given
        OrderService ordServ = new OrderService();
        String expected= "Picked";
        // when
        Optional<OrderStatus> actual = ordServ.getOrderStatus("a3712165-9a9a-4726-aeb6-e263f80635c0");
        // Then
        assertThat(actual.get().getStatus(),is(expected));

    }


}

