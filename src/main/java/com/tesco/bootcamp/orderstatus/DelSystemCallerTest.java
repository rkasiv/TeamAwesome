package com.tesco.bootcamp.orderstatus;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Created by MikeSamsung7 on 11/01/2017.
 */
public class DelSystemCallerTest {
    @Test
    public void getLatestTrackingEvent() throws Exception {
        String orderID = "a3712165-9a9a-4726-aeb6-e263f80635c0";
        DelSystemCaller delSystemCaller = new DelSystemCaller();
        TrackingEvent latestEvent = delSystemCaller.getLatestTrackingEvent(orderID);
        assertThat(latestEvent.getEventDateTime(), is ("2017-01-10T00:24:32.237+0000"));

    }


}