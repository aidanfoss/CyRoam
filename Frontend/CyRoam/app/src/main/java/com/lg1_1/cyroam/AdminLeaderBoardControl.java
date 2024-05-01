package com.lg1_1.cyroam;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminLeaderBoardControl extends AppCompatActivity {
    /**
     * tag for debugging
     */
    private String TAG = "AdminLeaderBoardControl";
    /**
     *
     * Takes url from Main
     */
    private String mainURL = MainActivity.url;


    /**
     * Is the back button off the screen
     */
    private Button backbutton;
    /**
     * Is a button that takes user
     */
    private Button UserSearch;
    /**
     * Button that Bans user
     */
    private Button banbutton;
    /**
     * holds the username from text edit to add
     */
    private EditText usernameEditText;
    /**
     * holds the score from text edit to add
     */
    private EditText scoreEditText;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_admin_leader_board_control);
        //backbutton = findViewById(R.id.ReturnAdminButton);
        UserSearch =  findViewById(R.id.adminscorebutton);
        banbutton = findViewById(R.id.bannedbutton);
        usernameEditText = findViewById(R.id.signup_username_edt);
        scoreEditText = findViewById(R.id.emailthe43t1st);
        /*
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* when signup button is pressed, use intent to switch to Signup Activity
                Intent intent = new Intent(AdminLeaderBoardControl.this, MainActivity.class);
                startActivity(intent);  // go to SignupActivity
            }
        });

                 */
        UserSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curUsername = usernameEditText.getText().toString();
                String value = scoreEditText.getText().toString();
                int score =Integer.parseInt(value);
                setScore(curUsername, score);
            }
        });
        banbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curUsername = usernameEditText.getText().toString();
                leaderBoardBan(curUsername);
            }
        });

    }
























    void setScore(String curUsername, int score){
        String url = mainURL + "/setScore";

        // Convert input to JSONObject
        JSONObject userInfo = new JSONObject();
        try{

            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            userInfo.put("username", curUsername);
            userInfo.put("score", score);



        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                userInfo,
                response -> {
                    try{


                        JSONArray jsonArray = response.getJSONArray("addfriend");
                        Log.i(TAG, "request success");
                        //outputtext.setText(curUsername + " Friends:\n");

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

    private void leaderBoardBan(String curUsername){
        String url = mainURL + "/userPermsAdmin";

        // Convert input to JSONObject
        JSONObject userInfo = new JSONObject();
        try{

            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            userInfo.put("username", curUsername);
            userInfo.put("promotion", -3);




        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                userInfo,
                response -> {
                    try{


                        JSONArray jsonArray = response.getJSONArray("addfriend");
                        Log.i(TAG, "request success");
                        //outputtext.setText(curUsername + " Friends:\n");

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

}