package com.lg1_1.cyroam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    private Button loginButton;         // define login button variable
    private Button signupButton;        // define signup button variable
    private boolean confirm = false;
    String output;
    private TextView textView;
    private String mainURL = MainActivity.url;
    private String TAG = "LoginActivity";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);            // link to Login activity XML

        /* initialize UI elements */
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);    // link to login button in the Login activity XML
        signupButton = findViewById(R.id.signupButton);  // link to signup button in the Login activity XML

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* grab strings from user inputs */
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                makeStringReq(username, password, new VolleyCallback() {
                    @Override
                    public void onSuccess(boolean isTrue) {
                        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                        intent.putExtra("LoginSuccess", isTrue);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });
               // if(confirm){//if confrim is true
                /* when login button is pressed, use intent to switch to Login Activity */
               // Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
//                intent.putExtra("USERNAME", username);  // key-value to pass to the MainActivity
//                intent.putExtra("PASSWORD", password);  // key-value to pass to the MainActivity
                //intent.putExtra("LONGITUDE", 42.023949);
               // intent.putExtra("LATITUDE", -93.647595);
               // intent.putExtra("NAME", "Library");
               // startActivity(intent);  // go to MainActivity with the key-value data
                  //  }
            }
        });


        /* click listener on login button pressed */
        //loginButton.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View v) {

                /* grab strings from user inputs */
               // String username = usernameEditText.getText().toString();
               // String password = passwordEditText.getText().toString();

               // /* when login button is pressed, use intent to switch to Login Activity */
              //  Intent intent = new Intent(LoginActivity.this, LoginInputActivity.class);
             //   intent.putExtra("USERNAME", username);  // key-value to pass to the MainActivity
              //  intent.putExtra("PASSWORD", password);  // key-value to pass to the MainActivity
               // startActivity(intent);  // go to MainActivity with the key-value data
            //}
       // });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* when signup button is pressed, use intent to switch to Signup Activity */

                    Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(intent);  // go to SignupActivity

            }
        });
    }
    private void makeStringReq(String curUsername, String password, final VolleyCallback callback){
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