package com.tesco.bootcamp.orderstatus;

import com.sun.deploy.net.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(StatusController.class)
public class StatusControllerTest {
    private static final String AN_ORDER_ID = "an order id";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void shouldReturnOrderStatus() throws Exception {
        OrderStatus orderStatus = new OrderStatus(AN_ORDER_ID, "DELIVERED");
        when(orderService.getOrderStatus(AN_ORDER_ID)).thenReturn(Optional.of(orderStatus));

        mvc.perform(get("/order-status").param("orderId", AN_ORDER_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\"orderId\":\"an order id\",\"status\":\"DELIVERED\"}"));
    }

    @Test
    public void shouldReturn404WhenStatusForOrderIdCannotBeFound() throws Exception {
        when(orderService.getOrderStatus(AN_ORDER_ID)).thenReturn(Optional.empty());
        mvc.perform(get("/order-status").param("orderId", AN_ORDER_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn400WhenOrderIdMissing() throws Exception {
        // given
            // not required
        // when
        mvc.perform(get("/order-status").accept(MediaType.APPLICATION_JSON))
        // then
                .andExpect(status().isBadRequest());
    }
}