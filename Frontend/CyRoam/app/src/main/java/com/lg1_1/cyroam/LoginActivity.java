package com.lg1_1.cyroam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    private Button loginButton;         // define login button variable
    private Button signupButton;        // define signup button variable
    private boolean confirm = false;
    private static final String URL_STRING_REQ = "https://jsonplaceholder.typicode.com/users/1";
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
                makeStringReq();
                if(confirm){//if confrim is true
                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
//                intent.putExtra("USERNAME", username);  // key-value to pass to the MainActivity
//                intent.putExtra("PASSWORD", password);  // key-value to pass to the MainActivity
                intent.putExtra("LONGITUDE", 42.023949);
                intent.putExtra("LATITUDE", -93.647595);
                intent.putExtra("NAME", "Library");
                startActivity(intent);  // go to MainActivity with the key-value data
                    }
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
    private void makeStringReq() {

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_STRING_REQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the successful response here
                        Log.d("Volley Response", response);
                        String sndhelpples = "true";
                        if(response.equals (sndhelpples)){
                            confirm = true;
                        }


                        //msgResponse.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley Error", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
//                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("param1", "value1");
//                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        //VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}