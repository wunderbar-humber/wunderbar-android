package com.wunderbar_humber.wunderbar.model;

import android.location.Location;
import android.util.Log;

import com.wunderbar_humber.wunderbar.webservice.yelp.YelpInitializeApiTask;
import com.wunderbar_humber.wunderbar.webservice.yelp.YelpSearchBusinessesTask;
import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class HomeModel {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Business> ITEMS = new ArrayList<Business>();
    public static final Map<String, Business> ITEM_MAP = new HashMap<String, Business>();
    private static final int COUNT = 25;
    public List<Business> businessList;
    private YelpFusionApi yelp;
    private Map<String, String> yelpSearchParams;

    /**
     * create home model with the users location
     */
    public HomeModel(Location location) {
        this(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
    }

    public HomeModel(String latitude, String longitude) {
        // create a yelp api instance
        try {
            yelp = new YelpInitializeApiTask().execute().get();
        } catch (InterruptedException e) {
            Log.e("Yelp Initialization", "Yelp API interrupted", e);
        } catch (ExecutionException e) {
            Log.e("Yelp Initialization", "Exception while initializing Yelp API", e);
        }
        yelpSearchParams = new HashMap<>();
        yelpSearchParams.put("categories", "restaurants, All, nightlife, All");
        setLatitude(latitude);
        setLongitude(longitude);

        searchRestaurants();
    }

    private static void addItem(Business item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }


    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * Search for restaurants at the current location on yelp
     */
    public void searchRestaurants() {

        Call<SearchResponse> searchCall = yelp.getBusinessSearch(yelpSearchParams);

        try {
            businessList = new YelpSearchBusinessesTask().execute(searchCall).get();
        } catch (InterruptedException e) {
            Log.e("Yelp Search", "Interrupted while searching", e);
        } catch (ExecutionException e) {
            Log.e("Yelp Search", "Execution exception while searching", e);
        }
    }


    public void setLocation(Location location) {
        setLatitude(String.valueOf(location.getLatitude()));
        setLongitude(String.valueOf(location.getLongitude()));
    }

    public void setLatitude(String latitude) {
        yelpSearchParams.put("latitude", latitude);
    }

    public void setLongitude(String longitude) {
        yelpSearchParams.put("longitude", longitude);
    }

    public void setSearchTerm(String term) {
        yelpSearchParams.put("term", term);
    }

    public List<Business> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<Business> businessList) {
        this.businessList = businessList;
    }
}
