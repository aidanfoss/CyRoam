package com.lg1_1.cyroam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FriendActivity extends AppCompatActivity {
    private static final String URL_STRING_REQ = "https://jsonplaceholder.typicode.com/users/1";
    private Button backbutton;

    private Button friendsearch;

    private final boolean found = false;

    private TextView outputtext;
    private EditText usernameEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        backbutton = findViewById(R.id.Backbutton);
        friendsearch =  findViewById(R.id.searchButton);
        outputtext = findViewById(R.id.Outputtext);
        usernameEditText = findViewById(R.id.usernamesearch);


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
                String username = usernameEditText.getText().toString();
                makeStringReq(username);
                if(!found){
                    outputtext.setText("User doesnt exist");
                }
                else{
                    outputtext.setText("Invite sent");
                }
            }
        });
    }
    private void makeStringReq(String user){
        String url = URL_STRING_REQ + "/signin";
        // Convert input to JSONObject
        JSONObject userInfo = new JSONObject();
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            userInfo.put("Username", user);


        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                userInfo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        /*if(response){
                            checker = true;
                        }*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}