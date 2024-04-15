package com.lg1_1.cyroam;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity that establishes anything used throughout the whole app
 * Automatically transitions you to the login activity.
 */
public class MainActivity extends AppCompatActivity {
	//test

    public static final String url = "http://coms-309-008.class.las.iastate.edu:8080";
    public static final String wsurl = "ws://coms-309-008.class.las.iastate.edu:8080";
//    public static final String wsurl = "ws://demo.piesocket.com/v3/channel_123?api_key=VCXCEuvhGcBDP7XhiJJUDvR1e1D3eiVjgZ9VRiaV&notify_self";

    //public aidanWebSocket aidanWebSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML

        /*For the time being, im going to call for the location permissions in the mapActivity.
        Theres a reason google says to call it in main, but it doesnt seem to matter
        at the moment so im just going to do it there until i run into that issue*/

        //Moves to login screen automatically
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
