package com.tesco.bootcamp.orderstatus;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OrderSystemCheckTest {

    @Test
    public void should_return_true_if_order_is_missing() {
        String wrongOrderId = "1234";
        OrderSystemCheck orderSystemCheck = new OrderSystemCheck("http://orders.dev-environment.tesco.codurance.io:8080/");

        boolean v = orderSystemCheck.isOrderMissing(wrongOrderId);

        assertThat(v, is(true));
    }
}