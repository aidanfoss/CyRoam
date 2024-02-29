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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;


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
                makeStringReq(username, password);
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
    private void makeStringReq(String pass, String user){
        String url = URL_STRING_REQ + "/login";
        // Convert input to JSONObject
        JSONObject userInfo = new JSONObject();
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            userInfo.put("Username", user);
            userInfo.put("Password", pass);

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