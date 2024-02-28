package com.lg1_1.cyroam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lg1_1.cyroam.volley.progressVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProgressActivity extends AppCompatActivity {

    //Debug or Logging stuff
    private String TAG = "ProgressActivity";

    //define xml features
    private Button postButton;
    private EditText pinIDText;
//    private dropDown pinSelector; //this is a better idea than an edittext but oh well

    //define progressVolley class
    private progressVolley volley; //this initializes the progressVolley class i made in the volley package


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

//        pinIDText = findViewById(R.id.pinIDEditText);
//        postButton = findViewById(R.id.postButton);

        volley = new progressVolley(this);

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