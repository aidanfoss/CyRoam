package com.lg1_1.cyroam.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lg1_1.cyroam.MainActivity;
import com.lg1_1.cyroam.Managers.LoginManager;

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
    private static progressVolley instance;

    /**
     * Constructor for the progressVolley object, allowing other classes to call
     * functions defined in this class.
     */
    private progressVolley(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context); //tutorial said to do this, but not sure why. couldn't it just be (this) for context?
    }
    public static progressVolley getInstance(Context context) {
        if (instance == null){
            instance = new progressVolley(context);
        }
        return instance;
    }

    /**
     * this function creates a new progress object
     * which logs discovery of a pin for a given player.
     * Uses volley to store that information in the database
     *
     * @author Aidan Foss
     * @param pinId the pin ID of the pin they are discovering
     * @param callback callback that handles errors and passes information
     */
    public void discoverPin(int pinId, final VolleyCallback callback){

        JSONObject requestBody = new JSONObject();
        try{
            requestBody.put("uId", LoginManager.getInstance().getUser().getID());
            requestBody.put("pId", pinId);
        } catch (JSONException e){
            e.printStackTrace();
            Log.e(TAG, "RequestBody JSONException occurred: " + e.getMessage());
            callback.onFailure("JSONException" + e.getMessage());
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "/users/" + LoginManager.getInstance().getUser().getID() + "/discovery/" + pinId, requestBody,
                response -> {
                    Log.v(TAG, "DISCOVERPIN RESPONSE: " + response.toString());
                    callback.onSuccess();
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
        void onSuccess();
        void onFailure(String errorMessage);
    }

    /**
     * @param callback handles errors and passes information
     */
    public void fetchProgress(final VolleyCallbackGet callback){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, BASE_URL + "/users/" + LoginManager.getInstance().getUser().getID() + "/discovery", null,
                response -> {
                    Log.i(TAG, "FETCHPIN RESPONSE: " + response.toString());
                    try {
                        int userId = response.getInt("userId");
                        int pinId = response.getInt("pinId");
                        boolean discovered = response.getBoolean("discovered");
                        callback.onSuccess(pinId,userId,discovered,userId);
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
     * Handles errors and passes information when calling pin discovery information
     * @author Aidan Foss
     */
    public interface VolleyCallbackGet {
        void onSuccess(int pinId, int userId, boolean discovered, int progressObjId);
        void onFailure(String errorMessage);
    }
}
