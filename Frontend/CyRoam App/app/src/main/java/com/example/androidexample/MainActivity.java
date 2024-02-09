package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;   // define message textview variable
    private TextView usernameText;  // define username textview variable

    private Button showMap;
    private Button loginButton;     // define login button variable
    private Button signupButton;    // define signup button variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML

        /* initialize UI elements */
        messageText = findViewById(R.id.main_msg_txt);      // link to message textview in the Main activity XML
        usernameText = findViewById(R.id.main_username_txt);// link to username textview in the Main activity XML
        showMap = findViewById(R.id.showMap);
        loginButton = findViewById(R.id.main_login_btn);    // link to login button in the Main activity XML
        signupButton = findViewById(R.id.main_signup_btn);  // link to signup button in the Main activity XML


        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });


        /* extract data passed into this activity from another activity */
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            messageText.setText("Home Page");
            usernameText.setVisibility(View.INVISIBLE);             // set username text invisible initially
        } else {
            messageText.setText("Welcome");
            usernameText.setText(extras.getString("USERNAME")); // this will come from LoginActivity
            //loginButton.setVisibility(View.INVISIBLE);              // set login button invisible
            //signupButton.setVisibility(View.INVISIBLE);             // set signup button invisible
        }

        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when login button is pressed, use intent to switch to Login Activity */
               Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

//        private void getLocationPermission() {
//            /*
//             * Request location permission, so that we can get the location of the
//             * device. The result of the permission request is handled by a callback,
//             * onRequestPermissionsResult.
//             */
//            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                    android.Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                locationPermissionGranted = true;
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//            }
//        }         GOOGLE RECOMMENDS DOING THIS. at the moment, i have it set to allow fine location
//                  regardless, but this is needed if we ever deploy to an actual android phone.
//                  if uncommented, would be broken and unusable. https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial#java_4

    }
}