package com.tesco.bootcamp.orderstatus;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by cx11 on 12/01/2017.
 */
public class TestOrderstatus {

    @Test
    public void TestGetOrderStatus() {

        OrderService ordServ = new OrderService("a3712165-9a9a-4726-aeb6-e263f80635c0");
        String expected = "Picked";
        String actual = ordServ.getOrderStatus();
        assertThat(actual,is(expected));

    }


}

