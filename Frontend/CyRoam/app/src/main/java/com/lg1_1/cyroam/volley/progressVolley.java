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

import org.json.JSONException;
import org.json.JSONObject;

public class progressVolley {

    private static final String TAG = "ProgressRequest";
    private static final String BASE_URL = MainActivity.url;

    private RequestQueue queue;
    private Context context;

    public progressVolley(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context); //tutorial said to do this, but not sure why. couldn't it just be (this) for context?
    }

    /*
    this function, when given its proper inputs, will set the called pins discover data to true.
     */
    public void discoverPin(int userId, int pinId, final VolleyCallback callback){
        String url = BASE_URL + "/progress";
        JSONObject requestBody = new JSONObject();
        try{
            requestBody.put("userId", userId);
            requestBody.put("pinId", pinId);
            requestBody.put("discovered", true); //always sets discovered to true when calling this function
        } catch (JSONException e){
            e.printStackTrace();
            callback.onFailure("JSONException" + e.getMessage());
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean discovered = response.getBoolean("discovered");
                            callback.onSuccess(discovered);
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
    public interface VolleyCallback {
        void onSuccess(boolean discovered);
        void onFailure(String errorMessage);
    }
}
