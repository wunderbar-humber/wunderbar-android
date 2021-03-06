package com.wunderbar_humber.wunderbar.webservice.yelp;

import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.models.Business;

import org.junit.Before;
import org.junit.Test;

import retrofit2.Call;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by mohit on 2018-01-01.
 */
public class YelpGetBusinessTaskTest {
    private YelpFusionApi api;

    @Before
    public void setUp() throws Exception {
        api = new YelpInitializeApiTask().execute().get();
    }

    @Test
    public void getBusiness() throws Exception {
        YelpGetBusinessTask task = new YelpGetBusinessTask();
        Call<Business> call = api.getBusiness("saffron-valley-south-jordan");
        Business business = task.execute(call).get();

        assertNull("There should be no exceptions", task.exception);
        assertEquals("saffron-valley-south-jordan", business.getId());
    }
}