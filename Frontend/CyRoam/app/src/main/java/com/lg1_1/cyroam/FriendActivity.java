package com.lg1_1.cyroam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Nicholas Kirschbaum
 * is the main friends activity for now with both the get and post on it
 */
public class FriendActivity extends AppCompatActivity {
    //private static final String URL_STRING_REQ = "https://speeding-space-815350.postman.co/workspace/Pins-and-Progress~dcaccb7e-5ba7-4d56-89e4-759bc8e4bc5d/request/32668124-b299505a-5abb-47b1-bd0f-c0d605462b0e?ctx=documentation";
    /**
     * @author Nicholas Kirschbaum
     * tag for debugging
     */
    private String TAG = "FriendActivity";
    /**
     * @author Nicholas Kirschbaum
     * Takes url from Main
     */
    private String mainURL = MainActivity.url;
    /**
     * @author Nicholas Kirschbaum
     * Is a button that takes user to portal screen
     */
    private Button backbutton;
    /**
     * @author Nicholas Kirschbaum
     * Is a button that sends invite to the user that is typed
     */
    private Button friendsearch;

    /**
     * @author Nicholas Kirschbaum
     * one of the
     */
    private TextView titletext;
    private TextView outputtext;
    private TextView textView3;
    private EditText usernameEditText;
    String output = null;
    String usernamestring;

    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        backbutton = findViewById(R.id.Backbutton);
        friendsearch =  findViewById(R.id.searchButton);
        outputtext = findViewById(R.id.Outputtext);
        usernameEditText = findViewById(R.id.usernamesearch);
        titletext = findViewById(R.id.textView3);
        Bundle extras = getIntent().getExtras();
        if(extras == null){

        }
        else{
            usernamestring = getIntent().getStringExtra("Username");
            String finallyer = usernamestring + "'s: Friends";
            titletext.setText(finallyer);
           //findfriendsReq(usernamestring);
        }


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(FriendActivity.this, MainActivity.class);
                startActivity(intent);  // go to SignupActivity
            }
        });
        friendsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* when signup button is pressed, use intent to switch to Signup Activity */
                String friendusername = usernameEditText.getText().toString();
                addfriendsReq(usernamestring,friendusername);
                //findfriendsReq(usernamestring);
                /*if(!found){
                    outputtext.setText(output);
                }
                else{
                    outputtext.setText(output);
                }*/
                //outputtext.setText(output);
            }
        });
    }
    private void findfriendsReq(String curUsername){
        String url = mainURL + "/friends/" + curUsername;
    /*
        // Convert input to JSONObject
        JSONObject userInfo = new JSONObject();
        try{

            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            userInfo.put("curUsername", curUsername);


        } catch (Exception e){
            e.printStackTrace();
        }
        */
        @SuppressLint("SetTextI18n") JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                //userInfo,
                response -> {
                    try{
                        JSONArray jsonArray = response.getJSONArray("friends");
                        Log.i(TAG, "request success");
                        outputtext.setText(curUsername + " Friends:\n");
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject friend = jsonArray.getJSONObject(i);

                            String curUser = friend.getString("curUsername");
                            String friendUser = friend.getString("friendUsername");
                            output = curUser + " " + friendUser;

                            outputtext.append(friendUser + "\n");
                            Log.i(TAG, output);


                        }

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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                //                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //                params.put("param1", "value1");
                //                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        queue.add(request);
    }



























    private void addfriendsReq(String curUsername,String Newfriend){
        String url = mainURL + "/addFriend/";

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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                //                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //                params.put("param1", "value1");
                //                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        queue.add(request);
    }
}