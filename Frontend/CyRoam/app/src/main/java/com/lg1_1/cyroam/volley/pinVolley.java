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

public class pinVolley {

    private static final String TAG = "PinRequest";
    private static final String BASE_URL = MainActivity.url;

    private RequestQueue queue;
    private Context context;

    public pinVolley(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context); //tutorial said to do this, but not sure why. couldn't it just be (this) for context?
    }

    public void fetchPinData(int pinId, final FetchPinCallback callback) {
        String url = BASE_URL + "/pins/" + pinId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e(TAG, "FETCHPIN RESPONSE: " + response.toString());
                            int id = response.getInt("id");
                            double x = response.getDouble("x");
                            double y = response.getDouble("y");
                            String name = response.getString("name");
                            Pin pin = new Pin(x, y, name, id);
                            callback.onSuccess(pin);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onFailure("JSONException: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error occured: " + error.getMessage());
                callback.onFailure("Error occured: " + error.getMessage());
            }
        });
    }

    /*
    this function, when given its proper inputs, will set the called pins discover data to true.
     */
    public void createPin(double x, double y, String name, final CreatePinCallback callback){
        Log.w(TAG, "createPin Called!");
        String url = BASE_URL + "/pins";
        Log.w(TAG, "Calling URL = " + BASE_URL + "/pins");
        JSONObject requestBody = new JSONObject();
        try{
            Log.w(TAG, String.valueOf(x) + " " + String.valueOf(y) + " " + name);
            requestBody.put("x", x);
            requestBody.put("y", y);
            requestBody.put("name", name);
        } catch (JSONException e){
            e.printStackTrace();
            callback.onFailure("JSONException" + e.getMessage());
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, " CREATEPIN RESPONSE: " + response.toString());
                        try {
                            int id = response.getInt("pinId");
                            Log.w(TAG, "pinId recieved");
                            callback.onSuccess(id);
                        } catch (JSONException e) {
                            Log.e(TAG, "JSONException: " + e.getMessage());
                            e.printStackTrace();
                            callback.onFailure("JSONException: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error occured: " + error.getMessage());
                callback.onFailure("Error occured: " + error.getMessage());
            }
        });
    }
    public interface CreatePinCallback {
        void onSuccess(int idSuccess);
        void onFailure(String errorMessage);
    }

    public interface FetchPinCallback {
        void onSuccess(Pin success);
        void onFailure(String errorMessage);
    }
}
