package com.lg1_1.cyroam;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.HashMap;
import java.util.Map;

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

    private RequestQueue queue;

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

    private void leaderBoardBan(String curUsername){
        String url = mainURL + "/deleteFriend/" + curUsername;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                response -> {
                    try{
                        JSONArray jsonArray = response.getJSONArray("leaderBoardBan");
                        Log.i(TAG, "request success");

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
                return headers;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        // Adding request to request queue
        queue.add(request);
    }
    private void setScore(String curUsername,int score){
        String url = mainURL + "/setScore/" + curUsername + "/" + score;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                response -> {
                    try{
                        JSONArray jsonArray = response.getJSONArray("setScore");
                        Log.i(TAG, "request success");

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
                return headers;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        // Adding request to request queue
        queue.add(request);
    }
}