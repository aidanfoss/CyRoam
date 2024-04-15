package com.lg1_1.cyroam.volley;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lg1_1.cyroam.MainActivity;
import com.lg1_1.cyroam.objects.User;

import org.json.JSONException;
import org.json.JSONObject;

public class userVolley {
    private static final String TAG = "userVolley";
    private final String BASE_URL = MainActivity.url;
    @SuppressLint("StaticFieldLeak")
    private static userVolley instance;
    private RequestQueue queue;
    private Context context;

    /**
     * userVolley constructor, should likely stay empty.
     * @author Aidan Foss
     */
    private userVolley(Context context){

    }

    public static userVolley getInstance(Context context) {
        if (instance == null) {
            instance = new userVolley(context);
        }
        return instance;
    }

    public void logInRequest(String username, String password, final logInCallback callback){
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
            callback.systemFailure("JSONException" + e.getMessage());
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
            response -> {
                Log.v(TAG, "LogInVolley Response: " + response.toString());
                try{
                    if(response.getString("username").equals(username)){
                        if(response.getBoolean("success")){ //if the server verifies the login information
                            int id = response.getInt("userID");
                            Log.v(TAG, "UserID Received " +  String.valueOf(response.getInt("userID")));
                            User outUser = new User(username, id, response);
                            callback.loggedIn(outUser);
                        }
                        else{
                            Log.v(TAG, "login failed!");
                            callback.logInFailure(response.getString("message"));
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, error -> {
                Log.e(TAG, "Error occured: " + error.getMessage());
                callback.systemFailure("Error occured: " + error.getMessage());
            });
        queue.add(request);
    }

    public interface logInCallback {
        void loggedIn(User user);
        void logInFailure(String failResponse);
        void systemFailure(String errorMessage);
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
