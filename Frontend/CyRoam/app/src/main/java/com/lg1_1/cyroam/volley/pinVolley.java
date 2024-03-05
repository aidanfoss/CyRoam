package com.lg1_1.cyroam.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.lg1_1.cyroam.MainActivity;
import com.lg1_1.cyroam.util.Pin;

import org.json.JSONException;
import org.json.JSONObject;

/*
This class uses interfaces to allow easier use in other classes.
There is no attached activity or XML.
Handles volley requests specifically for pin data.
 */

public class pinVolley {

    private static final String TAG = "PinRequest";
    private static final String BASE_URL = MainActivity.url;

    private RequestQueue queue;
    private Context context;

    public pinVolley(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context); //tutorial said to do this, but not sure why. couldn't it just be (this) for context?
    }

    //TODO create update volley request
    public void updatePinData(int pinId, final UpdatePinCallback callback){
    }
    public interface UpdatePinCallback {
        void onSuccess(int idSuccess);
        void onFailure(String errorMessage);
    }


    /*
    this function fetches a specific pins object.
    pinId is likely the built in ID created by SQL
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
    public interface FetchPinCallback {
        void onSuccess(Pin pin);
        void onFailure(String errorMessage);
    }

    /*
    this function creates a new pin.
    server responds with the ID of the pin that's created.
    Then, in onSuccess in MapsActivity, can call get request to initialize it.
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
                    Log.d(TAG, " CREATEPIN RESPONSE: " + response.toString());
                    try {
                        int id = response.getInt("id");
                        Log.v(TAG, "id recieved");
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
    public interface CreatePinCallback {
        void onSuccess(int idSuccess);
        void onFailure(String errorMessage);
    }
}
