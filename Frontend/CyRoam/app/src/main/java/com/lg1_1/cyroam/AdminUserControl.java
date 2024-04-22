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

public class AdminUserControl extends AppCompatActivity {
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
    private Button promotebutton;
    /**
     * Button that Bans user
     */
    private Button banbutton;
    /**
     * holds the username from text edit to add
     */
    private EditText usernameEditText;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_admin_user_control);
        backbutton = findViewById(R.id.ReturnAdminButtonC);
        banbutton =  findViewById(R.id.adminc_BAN_btn);
        promotebutton = findViewById(R.id.Promote_btn);
        usernameEditText = findViewById(R.id.admin2_username_edt);

        promotebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curUsername = usernameEditText.getText().toString();
                promoteReq(curUsername);
            }
        });
        banbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curUsername = usernameEditText.getText().toString();
                banRequest(curUsername);
            }
        });
    }
    private void banRequest(String curUsername){
        String url = mainURL + "/setScore/" + curUsername + "/";

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


        };
        // Adding request to request queue
        queue.add(request);
    }
    private void promoteReq(String curUsername){
        String url = mainURL + "/setScore/" + curUsername + "/";

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

        };
        // Adding request to request queue
        queue.add(request);
    }
}