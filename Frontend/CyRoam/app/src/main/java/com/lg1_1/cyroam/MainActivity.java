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

    public static final String url = "https://8627db7c-2a59-41f6-9043-0b24f9f5dafa.mock.pstmn.io";

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
