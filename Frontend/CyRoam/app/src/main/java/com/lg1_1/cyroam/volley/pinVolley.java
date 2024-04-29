package com.lg1_1.cyroam.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.lg1_1.cyroam.MainActivity;
import com.lg1_1.cyroam.objects.Pin;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles volley requests specifically for pin data.
 * @author Aidan Foss
 * This class uses interfaces to allow easier use in other classes.
 * There is no attached activity or XML.
 */

public class pinVolley {

    private static final String TAG = "PinRequest";
    private static final String BASE_URL = MainActivity.url;
    private pinVolley instance;

    private final RequestQueue queue;

    /**
     * constructor for pinVolley.
     * @author Aidan Foss
     * @param context uses "this" in all cases
     */
    public pinVolley(Context context){
        queue = Volley.newRequestQueue(context); //tutorial said to do this, but not sure why. couldn't it just be (this) for context?
    }

    public pinVolley getInstance(Context cont) {
        if (instance == null){
            instance = new pinVolley(cont);
        }
        return instance;
    }

    public void updatePinData(int pinId, final UpdatePinCallback callback){
    }


    /**
     * Handles errors and passes information when updating pins
     * @author Aidan Foss
     */
    public interface UpdatePinCallback {
        void onSuccess(int idSuccess);
        void onFailure(String errorMessage);
    }

    /**
     * @author Aidan Foss
     * Fetches a specific pin object from the database
     * @param pinId recieves the pinId either from the user or from other means (onclickhandler, for ex)
     * @param callback sets up an on success and on error underneath the call for fetchPinData
     */
    public void fetchPinData(int pinId, final FetchPinCallback callback) {
        String url = BASE_URL + "/pins/" + pinId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Log.d(TAG, "FETCHPIN RESPONSE: " + response.toString());
                        int id = response.getInt("id");
                        double x = response.getDouble("x");
                        double y = response.getDouble("y");
                        String name = response.getString("name");
                        Pin pin = new Pin(x, y, name, id);
                        callback.onSuccess(pin);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "JSONException occured: " + e.getMessage());
                        callback.onFailure("JSONException: " + e.getMessage());
                    }
                }, error -> {
                    Log.e(TAG, "Error occured: " + error.getMessage());
                    callback.onFailure("Error occured: " + error.getMessage());
                });
        queue.add(request);
    }

    /**
     * Handles errors and passes information when calling pins
     * @author Aidan Foss
     */
    public interface FetchPinCallback {
        void onSuccess(Pin pin);
        void onFailure(String errorMessage);
    }

    /*
    this function creates a new pin.
    server responds with the ID of the pin that's created.
    Then, in onSuccess in MapsActivity, can call get request to initialize it.
    */

    /**
     *
     * @param x x coordinate of the pin to be created
     * @param y y coordinate of the pin to be created
     * @param name name of the pin to be created
     * @param callback callback input for in-line handling
     * Server replies with the ID of the pin thats created
     */
    public void createPin(double x, double y, String name, final CreatePinCallback callback){
        Log.v(TAG, "createPin Called!");
        String url = BASE_URL + "/pins";
        Log.d(TAG, "Calling URL = " + BASE_URL + "/pins");
        JSONObject requestBody = new JSONObject();
        try{
            Log.d(TAG, String.valueOf(x) + " " + String.valueOf(y) + " " + name);
            requestBody.put("x", x);
            requestBody.put("y", y);
            requestBody.put("name", name);
        } catch (JSONException e){
            Log.e(TAG, "JSONException" + e.getMessage());
            e.printStackTrace();
            callback.onFailure("JSONException" + e.getMessage());
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> {
                    Log.v(TAG, " CREATEPIN RESPONSE: " + response.toString());
                    try {
                        int id = response.getInt("id");
                        Log.w(TAG, "pinId recieved");
                        callback.onSuccess(id);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException: " + e.getMessage());
                        e.printStackTrace();
                        callback.onFailure("JSONException: " + e.getMessage());
                    }
                }, error -> {
                    Log.e(TAG, "Error occured: " + error.getMessage());
                    callback.onFailure("Error occured: " + error.getMessage());
                });
        queue.add(request);
    }

    /**
     * Handles errors and passes information when creating pins
     * @author Aidan Foss
     */
    public interface CreatePinCallback {
        void onSuccess(int idSuccess);
        void onFailure(String errorMessage);
    }
}
