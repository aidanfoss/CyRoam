/*
this activity is solely to request permissions and establish internet connection.
after this, moves directly to the login screen, which can bring you to the signup screen
if needed. For now, the login screen doesnt require a valid login.
 */


package com.lg1_1.cyroam;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static final String url = "http://coms-309-008.class.las.iastate.edu:8080";
//    public static final String wsurl = "ws://coms-309-008.class.las.iastate.edu:8080";
    public static final String wsurl = "ws://demo.piesocket.com/v3/channel_123?api_key=VCXCEuvhGcBDP7XhiJJUDvR1e1D3eiVjgZ9VRiaV&notify_self";

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
