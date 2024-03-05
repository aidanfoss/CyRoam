package com.lg1_1.cyroam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lg1_1.cyroam.volley.progressVolley;

public class ProgressActivity extends AppCompatActivity {

    //Debug or Logging stuff
    private String TAG = "ProgressActivity";

    //define xml features
    private Button postButton;
    private Button getButton;
    private EditText pinIDText;
    private Button backButton;

    //define progressVolley class
    private progressVolley volley; //this initializes the progressVolley class i made in the volley package


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        getButton = findViewById(R.id.getButton);
        pinIDText = findViewById(R.id.pinIDEditText);
        postButton = findViewById(R.id.postButton);
        backButton = findViewById(R.id.backButton);

        volley = new progressVolley(this);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinIdString = pinIDText.getText().toString().trim();
                if (!pinIdString.isEmpty()) {
                    try {
                        int pinId = Integer.parseInt(pinIdString);
                        volley.fetchProgress(pinId, new progressVolley.VolleyCallbackGet() {
                            @Override
                            public void onSuccess(int pinId, int userId, boolean discovered, int progressObjId) {
                                Log.d(TAG, "Pin Discovered: " + discovered);

                                //return to maps activity
                                Intent intent = new Intent(ProgressActivity.this, MapsActivity.class);
                                //add extra returned information here
                                intent.putExtra("discovered", discovered);
                                intent.putExtra("pinId", pinId);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                Log.e(TAG, "Get Error: " + errorMessage);
                            }
                        });
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Invalid Input for pinID integer");
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgressActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        //uses editText to get pinID, then uses helper class to make a volley post request to the users given Pin, which marks it as discovered.
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinIdString = pinIDText.getText().toString().trim(); //trim fixes any spaces, which can cause errors
                if (!pinIdString.isEmpty()) {
                    try {
                        int pinId = Integer.parseInt(pinIdString);
                        int userId = 1; //TODO FIX HARDCODED, later get userdata in from extra from maps screen

                        //Call discoverPin method
                        volley.discoverPin(userId, pinId, new progressVolley.VolleyCallback() {
                            @Override
                            public void onSuccess(boolean discovered) { //handles success
                                Log.d(TAG, "Pin Discovered: " + discovered);

                                //return to maps activity
                                Intent intent = new Intent(ProgressActivity.this, MapsActivity.class);
                                //add extra returned information here
                                intent.putExtra("discovered", discovered);
                                intent.putExtra("pinId", pinId);
                                startActivity(intent);
                            }
                            @Override
                            public void onFailure(String errorMessage) { //handles failure
                                Log.e(TAG, "Error: " + errorMessage);
                            }
                        });
                    } catch (NumberFormatException e){ //handle invalid input
                        Log.e(TAG, "Invalid Input for pinID integer");
                    }
                } else { //handle empty pin ID
                    Log.e(TAG, "Pin ID is empty");
                }
            }
        });

    }
}