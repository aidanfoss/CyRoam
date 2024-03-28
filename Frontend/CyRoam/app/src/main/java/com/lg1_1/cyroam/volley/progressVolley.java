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

/**
 * Handles volley requests specifically for pin data.
 * @author Aidan Foss
 */
public class progressVolley {

    private static final String TAG = "progressVolley";
    private static final String BASE_URL = MainActivity.url;
    private RequestQueue queue;
    private Context context;

    /**
     * Constructor for the progressVolley object, allowing other classes to call
     * functions defined in this class.
     * @param context should always be constructed with "this"
     */
    public progressVolley(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context); //tutorial said to do this, but not sure why. couldn't it just be (this) for context?
    }

    /**
     * this function creates a new progress object
     * which logs discovery of a pin for a given player.
     * Uses volley to store that information in the database
     *
     * @author Aidan Foss
     * @param userId the user ID of the player
     * @param pinId the pin ID of the pin they are discovering
     * @param callback callback that handles errors and passes information
     */
    public void discoverPin(int userId, int pinId, final VolleyCallback callback){

        JSONObject requestBody = new JSONObject();
        try{
            requestBody.put("userId", userId);
            requestBody.put("pinId", pinId);
            requestBody.put("discovered", true); //always sets discovered to true when calling this function
        } catch (JSONException e){
            e.printStackTrace();
            Log.e(TAG, "RequestBody JSONException occurred: " + e.getMessage());
            callback.onFailure("JSONException" + e.getMessage());
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "/progress", requestBody,
                response -> {
                    Log.e(TAG, "DISCOVERPIN RESPONSE: " + response.toString());
                    try {
                        boolean discovered = response.getBoolean("discovered");
                        Log.i(TAG, "response: " + String.valueOf(discovered));
                        callback.onSuccess(discovered);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException occurred: " + e.getMessage());
                        e.printStackTrace();
                        callback.onFailure("JSONException: " + e.getMessage());
                    }
                }, error -> {
                    Log.e(TAG, "Error occurred: " + error.getMessage());
                    callback.onFailure("Error occurred: " + error.getMessage());
                });
        queue.add(request);
    }

    /**
     * Handles errors and passes information when discovering pins
     * @author Aidan Foss
     */
    public interface VolleyCallback {
        void onSuccess(boolean discovered);
        void onFailure(String errorMessage);
    }

    /**
     * @deprecated because we've moved away from individual progress objects for each user and pin.
     * @param progressId the ID of the progress object stored in the server.
     * @param callback handles errors and passes information
     */
    public void fetchProgress(int progressId, final VolleyCallbackGet callback){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, BASE_URL + "/progress/" + String.valueOf(progressId), null,
                response -> {
                    Log.i(TAG, "FETCHPIN RESPONSE: " + response.toString());
                    try {
                        int userId = response.getInt("userId");
                        int pinId = response.getInt("pinId");
                        boolean discovered = response.getBoolean("discovered");
                        callback.onSuccess(pinId,userId,discovered,progressId);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error occured: " + e.getMessage());
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
     * @deprecated
     * Handles errors and passes information when calling pin discovery information
     * @author Aidan Foss
     */
    public interface VolleyCallbackGet {
        void onSuccess(int pinId, int userId, boolean discovered, int progressObjId);
        void onFailure(String errorMessage);
    }
}
