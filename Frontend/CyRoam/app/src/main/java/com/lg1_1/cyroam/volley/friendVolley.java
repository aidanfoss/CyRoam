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
import com.lg1_1.cyroam.util.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class friendVolley {

    private static final String TAG = "PinRequest";
    private static final String BASE_URL = MainActivity.url;

    private RequestQueue queue;
    private Context context;

    public friendVolley(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context); //tutorial said to do this, but not sure why. couldn't it just be (this) for context?
    }








    public void addfriendsReq(String curUsername, User Newfriend, final addFriendCallback callback){
        String url = BASE_URL + "/addFriend";
        // Convert input to JSONObject
        JSONObject userInfo = new JSONObject();
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            userInfo.put("curUsername", curUsername);
            userInfo.put("friendUsername", Newfriend);
        } catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                userInfo,
                response -> {
                    try{

                        JSONArray jsonArray = response.getJSONArray("addfriend");
                        Log.i(TAG, "request success");
                        //outputtext.setText(curUsername + " Friends:\n");
                        // for (int i = 0; i < jsonArray.length(); i++){
                        //  JSONObject friend = jsonArray.getJSONObject(i);

                        //  String curUser = friend.getString("curUsername");
                        // String friendUser = friend.getString("friendUsername");
                        // output = curUser + " " + friendUser;

                        //outputtext.append(friendUser + "\n");
                        //Log.i(TAG, output);


                        //}

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    // output = response.toString();

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,error.getMessage());
                        // tvResponse.setText(error.getMessage());
                    }
                }
        ){

        };

        // Adding request to request queue
        queue.add(request);
    }
    public interface addFriendCallback {
        void onSuccess(User user);
        void onFailure(String errorMessage);
    }


}
