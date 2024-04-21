package com.lg1_1.cyroam;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    private String TAG = "SettingsActivity";
    /**
     * Takes url from Main
     * @author Nicholas Kirschbaum
     */
    private String mainURL = MainActivity.url;

    //private Button backbuttontoportal;

    private Button removeButton;

    private EditText removefriend;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        ConstraintLayout constraintLayout = findViewById(R.id.settingScreenId);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        //backbuttontoportal = findViewById(R.id.settingsbackbutton);
        removeButton =  findViewById(R.id.buttonRemoveFriend);
        removefriend = findViewById(R.id.textViewRemoveFriend);
        /*
        backbuttontoportal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsActivity.this, PortalScreenActivity.class);
                startActivity(intent);  // go to SignupActivity
            }
        }); */


        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* when signup button is pressed, use intent to switch to Signup Activity */
                String curUsername = "bossf";
                String noLongerfriend = removefriend.getText().toString();
                removefriend(curUsername, noLongerfriend);

            }
        });

    }






    private void removefriend(String curUsername,String Newfriend){
        String url = mainURL + "/deleteFriend/" + curUsername + "/" + Newfriend;

        // Convert input to JSONObject
        /*JSONObject userInfo = new JSONObject();
        try{

            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            userInfo.put("curUsername", curUsername);
            userInfo.put("friendUsername", Newfriend);


        } catch (Exception e){
            e.printStackTrace();
        }
        */
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                response -> {
                    try{


                        JSONArray jsonArray = response.getJSONArray("deleteFriend");
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