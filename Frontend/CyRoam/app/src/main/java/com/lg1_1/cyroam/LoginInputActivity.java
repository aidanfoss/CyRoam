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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginInputActivity extends AppCompatActivity {
    // This while be the call to which ever table Zach makes
    private String TAG = "FriendActivity";
    private String mainURL = MainActivity.url;
    //The text in the username/password edittext
    private EditText usernameEditText;
    private EditText passwordEditText;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
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
                                Log.e(TAG, "make string req onFailure " + errorMessage);
                            }

                        });

                Intent intent = new Intent(LoginInputActivity.this, AddFriends.class);
               //intent.putExtra("USERNAME", username);  // key-value to pass to the MainActivity
                //intent.putExtra("PASSWORD", password);  // key-value to pass to the MainActivity
              //intent.putExtra("Username", username);
                startActivity(intent);
            }
        });
    }







    //this calls the server and checks to see if it correct
    private void makeStringReq(String curUsername, String password, final LoginActivity.VolleyCallback callback){
        //private void makeStringReq(String curUsername, String password){
        String url = mainURL + "/userCheck/" + curUsername + "/" + password;

        // Convert input to JSONObject
        /*
        JSONObject userInfo2 = new JSONObject();
        try{

            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb

            userInfo2.put("username", curUsername);
            userInfo2.put("password", password);
            Log.v(TAG, "userinfo try success " + userInfo2.toString());


        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "obj error: " + e.getMessage());
        }
        */
        //my name is only here because i ran rebugging for nick
        //for some reason he cant run a server connection on his laptop.
        //-aidan
      //  JsonRequest request = new JsonRequest(
        // JsonArrayRequest request = new JsonArrayRequest(
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                //userInfo2,
                // so the issue is that its saying that there is an error
                //Log.v(TAG, "respo success: "),
                response -> {
                    Log.v(TAG, "respo success: " + response.toString());
                    try{
                        Log.v(TAG, "request success");
                        boolean isTrue = response.getBoolean("isUser");
                        //return isTrue;
                        callback.onSuccess(isTrue);

                    }catch (JSONException e){
                        Log.e(TAG, "catch error: " + e.getMessage());
                        e.printStackTrace();
                        callback.onFailure(e.getMessage());
                    }

                    // output = response.toString();

                },
                error -> {
                    Log.e(TAG, "error lambda error: " + error.getMessage());
                    // tvResponse.setText(error.getMessage());
                }

        ){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
//                //                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                //                params.put("param1", "value1");
//                //                params.put("param2", "value2");
//                return params;
//            }
        };

        // Adding request to request queue
        queue.add(request);
//        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
    public interface VolleyCallback{
        void onSuccess(boolean isTrue);
        void onFailure(String errorMessage);
    }

}