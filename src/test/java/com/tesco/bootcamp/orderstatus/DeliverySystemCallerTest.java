package com.tesco.bootcamp.orderstatus;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by MikeSamsung7 on 11/01/2017.
 */
public class DeliverySystemCallerTest {
    private static final String AN_ORDER_ID = "An_order_id";
    private static final String A_PARCEL_ID = "An_parcel_id";

    @Test
    public void test_getLatestTrackingEvent_valid_orderID() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        DeliverySystemCaller deliverySystemCaller = new DeliverySystemCaller(restTemplate);
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        mockGhsOrderEventsRequest(server);
        mockGhsParcelEventsRequest(server);

        TrackingEvent latestEvent = deliverySystemCaller.getLatestTrackingEvent(AN_ORDER_ID);

        assertThat(latestEvent.getParcelID(), is(A_PARCEL_ID));
        assertThat(latestEvent.getEventDateTime(), is("2017-01-10T00:24:32.237+0000"));
    }

    @Test
    public void test_getLatestTrackingEvent() throws Exception {

        DeliverySystemCaller deliverySystemCaller = new DeliverySystemCaller();
        String validOrderID = "f7acafe4-876e-4d63-af36-216dfcd1729c";

        TrackingEvent latestEvent = deliverySystemCaller.getLatestTrackingEvent(validOrderID);

        assertThat(latestEvent.getEventDateTime(), is("2017-01-13T12:44:50.275+0000"));
    }

    @Test
    public void test_getLatestTrackingEvent_OrderID_Empty_String() throws Exception {
        String orderID = "";
        RestTemplate restTemplate = new RestTemplate();
        DeliverySystemCaller deliverySystemCaller = new DeliverySystemCaller(new RestTemplate());
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        mockNoEventRequest(server);

        TrackingEvent latestEvent = deliverySystemCaller.getLatestTrackingEvent(AN_ORDER_ID);

        assertThat(latestEvent.getEventType(), is("NO_EVENT"));

    }


    private void mockGhsParcelEventsRequest(MockRestServiceServer server) {
        server.expect(once(), requestTo("http://delivery.dev-environment.tesco.codurance.io:8080/events/ghs/parcel?parcelId=" + A_PARCEL_ID))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[{" +
                        "    \"eventType\": \"PARCEL_DELIVERED\",\n" +
                        "    \"vanId\": \"1d21404a-ae82-4685-a9d6-7b46184cd6d6\",\n" +
                        "    \"parcelId\": \"" + A_PARCEL_ID + "\",\n" +
                        "    \"eventDateTime\": \"2017-01-10T00:24:32.237+0000\"\n" +
                        "  }" +
                        " ,{" +
                        "    \"eventType\": \"PARCEL_ATTEMPTING_DELIVERY\",\n" +
                        "    \"vanId\": \"1d21404a-ae82-4685-a9d6-7b46184cd6d6\",\n" +
                        "    \"parcelId\": \"" + A_PARCEL_ID + "\",\n" +
                        "    \"eventDateTime\": \"2017-01-10T00:19:32.157+0000\"\n" +
                        "  }]", MediaType.APPLICATION_JSON));
    }

    private void mockGhsOrderEventsRequest(MockRestServiceServer server) {
        server.expect(once(), requestTo("http://delivery.dev-environment.tesco.codurance.io:8080/events/ghs/order?orderId=" + AN_ORDER_ID))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[ {\n" +
                        "    \"eventType\": \"PARCEL_DELIVERED\"," +
                        "    \"vanId\": \"16597585-f915-4065-b650-c7065457b8a5\"," +
                        "    \"parcelId\": \"" + A_PARCEL_ID + "\"," +
                        "    \"eventDateTime\": \"2017-01-11T17:09:49.878+0000\"" +
                        "  }]", MediaType.APPLICATION_JSON));
    }

    private void mockNoEventRequest(MockRestServiceServer server) {
        server.expect(once(), requestTo("http://delivery.dev-environment.tesco.codurance.io:8080/events/ghs/order?orderId=" + AN_ORDER_ID))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[ {\n" +
                        "    \"eventType\": \"PARCEL_DELIVERED\"," +
                        "    \"vanId\": \"16597585-f915-4065-b650-c7065457b8a5\"," +
                        "    \"parcelId\": \"" + A_PARCEL_ID + "\"," +
                        "    \"eventDateTime\": \"2017-01-11T17:09:49.878+0000\"" +
                        "  }]", MediaType.APPLICATION_JSON));
    }
}
