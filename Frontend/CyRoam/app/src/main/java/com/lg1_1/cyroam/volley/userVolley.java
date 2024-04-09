package com.lg1_1.cyroam.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lg1_1.cyroam.MainActivity;

import org.json.JSONObject;

public class userVolley {
    private static final String TAG = "userVolley";
    private final String BASE_URL = MainActivity.url;
    private static userVolley instance;
    private RequestQueue queue;
    private Context context;

    /**
     * userVolley constructor
     * @author Aidan Foss
     * @param context uses "this" in all cases
     */
    public userVolley(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public static userVolley getInstance(Context cont) {
        if (instance == null) {
            instance = new userVolley(cont);
        }
        return instance;
    }

    public int logInRequest(String username, String password, final logInCallback callback){
        Log.v(TAG, "logInRequest Called!");
        String url = BASE_URL + "/userCheck";
        Log.d(TAG, "Calling URL = " + BASE_URL + "/userCheck");
        JSONObject requestBody = new JSONObject();
        try{
            requestBody.put("username", username);
            requestBody.put("password", password);

        } catch (Exception e) {
            Log.e(TAG, "JSONException" + e.getMessage());
            e.printStackTrace();
            callback.onFailure("JSONException" + e.getMessage());
            return -99;
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
            response -> {
                Log.v(TAG, "LogInVolley Response: " + response.toString());
                try{
                    int id = response.getInt("userID");
                    Log.v(TAG, "UserID Received " +  String.valueOf(response.getInt("userID")));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, error -> {
                Log.e(TAG, "Error occured: " + error.getMessage());
                callback.onFailure("Error occured: " + error.getMessage());
            });
        queue.add(request);
        return -99; //error return
    }

    public interface logInCallback {
        void onSuccess(int userID);
        void onFailure(String errorMessage);
    }

    /**
     * Might be useful if we ever want to implement updating a username
     * @param oldUser
     * @param newUser
     * @param password
     * @param ID
     * @param callback
     */
    public void updateUsername(String oldUser, String newUser, String password, int ID, final updateCallback callback){
        Log.v(TAG, "updateUsername called!");
        String url = BASE_URL + "/userUpdate";
        JSONObject requestBody = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
            response -> {
                try{
                    boolean success = response.getBoolean("success");
                    String serverMessage = response.getString("serverMessage");
                    if (success){
                        callback.onSuccess();
                    } else {
                        callback.notChanged(serverMessage);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "JSONException: " + e.getMessage());
                    e.printStackTrace();
                    callback.onFailure("JSONException: " + e.getMessage());
                }
            }, error -> {
            Log.e(TAG, "Error occured: " + error.getMessage());
            callback.onFailure("Error occured: " + error.getMessage());
        });
    }
    public interface updateCallback {
        void onSuccess();
        void notChanged(String serverMessage);
        void onFailure(String errorMessage);
    }
}
