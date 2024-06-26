package com.lg1_1.cyroam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
/**
 * Signup Activity creates your account and then takes you to
 *@author Nicholas Kirschbaum
 *
 */
public class SignupActivity extends AppCompatActivity {
    /**
     * @author Nicholas Kirschbaum
     * Tags activity for debugging
     */
    private String TAG = "FriendActivity";
    /**
     * @author Nicholas Kirschbaum
     * takes url from MainActivity
     */
    private String mainURL = MainActivity.url;
    /**
     * @author Nicholas Kirschbaum
     * Takes input frim edit text and uses it as Username
     */
    private EditText usernameEditText;  // define username edittext variable
    /**
     * @author Nicholas Kirschbaum
     * Takes input frim edit text and user it as password
     */
    private EditText passwordEditText;  // define password edittext variable
    /**
     * @author Nicholas Kirschbaum
     * Takes input frim edit text and ckecks to see if password matchs
     */
    private EditText confirmEditText;   // define confirm edittext variable
    /**
     * @author Nicholas Kirschbaum
     * Takes you to login scren
     */
    private Button loginButton;         // define login button variable
    /**
     * @author Nicholas Kirschbaum
     * Runs post and takes you to login screen
     */
    private Button signupButton;        // define signup button variable
    /**
     * @author Nicholas Kirschbaum
     * Is the Request queue
     */

    private RequestQueue queue;
    /**
     * Stores the users email
     */
    private EditText email;
    private TextView test;
    //private boolean checker = false;
    /**
     * @author Nicholas Kirschbaum
     * loads the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        /**
         * Background is from
         * <a href="http://www.freepik.com">Designed by rawpixel.com / Freepik</a>
         */
        setContentView(R.layout.activity_signup);

        /* initialize UI elements */
        //email = findViewById(R.id.emailthe1st);


        //pleasedosomething
        usernameEditText = findViewById(R.id.signup_username_edt);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.signup_password_edt);  // link to password edtext in the Signup activity XML
        confirmEditText = findViewById(R.id.signup_confirm_edt);    // link to confirm edtext in the Signup activity XML
        loginButton = findViewById(R.id.signup_login_btn);    // link to login button in the Signup activity XML
        signupButton = findViewById(R.id.signup_signup_btn);  // link to signup button in the Signup activity XML
        test = findViewById(R.id.textView12log);
        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //test.setText("success");
                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(SignupActivity.this, LoginInputActivity.class);
                startActivity(intent);  // go to LoginActivity
            }
        });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_ = "";
                /* grab strings from user inputs */
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirm = confirmEditText.getText().toString();
                //email_ = email.getText().toString();

                if (password.equals(confirm)){
                    Toast.makeText(getApplicationContext(), "Signing up", Toast.LENGTH_LONG).show();
                    makePostReq(password, username);
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    /**
     * @author Nicholas Kirschbaum
     * Contacts the server to create a user using the infromation you provided
     */
    private void makePostReq(String pass, String user){
        String url = mainURL + "/users";
        Log.d(TAG,"post req called " + url);

        // Convert input to JSONObject
        JSONObject userInfo = new JSONObject();
        try{

            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            userInfo.put("username", user);
            userInfo.put("password", pass);
           // userInfo.put("email", email);
            Log.v(TAG, "JSON OBJECT CREATED");


        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                //null,
                userInfo,
                    response -> {
                        Log.d(TAG, "Request successful");
                        Toast.makeText(getApplicationContext(), "Signup successful! You can now log in.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        // Handle the response as needed
                    },
                    error -> {
                        Log.e(TAG, "Error making POST request: " + error.getMessage());
                        // Handle error appropriately, e.g., display a message to the user
                    }
        ){

        };

        // Adding request to request queue
        queue.add(request);
    }
}