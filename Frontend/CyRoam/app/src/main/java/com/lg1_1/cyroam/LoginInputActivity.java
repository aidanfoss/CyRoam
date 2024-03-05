package com.lg1_1.cyroam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginInputActivity extends AppCompatActivity {
    // This while be the call to which ever table Zack makes
    private String TAG = "FriendActivity";
    private String mainURL = MainActivity.url;
    //The text in the username/password edittext
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_input);
        Button loginButton = findViewById(R.id.Loginbutton);


        usernameEditText = findViewById(R.id.editTextTextUsername);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                makeStringReq(username, password, new LoginActivity.VolleyCallback() {
                            @Override
                            public void onSuccess(boolean isTrue) {
                                Intent intent = new Intent(LoginInputActivity.this, FriendActivity.class);
                                intent.putExtra("Username", username);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(String errorMessage) {

                            }
                        });
               //Intent intent = new Intent(LoginInputActivity.this, FriendActivity.class);
               // intent.putExtra("USERNAME", username);  // key-value to pass to the MainActivity
                //intent.putExtra("PASSWORD", password);  // key-value to pass to the MainActivity
              //intent.putExtra("Username", username);
              //startActivity(intent);
            }
        });
    }







    //this calls the server and checks to see if it correct
    private void makeStringReq(String curUsername, String password, final LoginActivity.VolleyCallback callback){
        String url = mainURL + "/userCheck";

        // Convert input to JSONObject
        JSONObject userInfo = new JSONObject();
        try{

            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb

            userInfo.put("username", curUsername);
            userInfo.put("password", password);


        } catch (Exception e){
            e.printStackTrace();
        }

        @SuppressLint("SetTextI18n") JsonObjectRequest request = new JsonObjectRequest(

                Request.Method.GET,
                url,
                userInfo,
                response -> {
                    //Log.i(TAG, "respo success")
                    try{
                        Log.i(TAG, "request success");
                        boolean isTrue = response.getBoolean("isUser");
                        //return isTrue;
                        callback.onSuccess(isTrue);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    // output = response.toString();

                },
                error -> {
                    Log.e(TAG,error.getMessage());
                    // tvResponse.setText(error.getMessage());
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
    public interface VolleyCallback{
        void onSuccess(boolean isTrue);
        void onFailure(String errorMessage);
    }

}