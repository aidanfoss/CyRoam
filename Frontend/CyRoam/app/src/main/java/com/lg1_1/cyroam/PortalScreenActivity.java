package com.lg1_1.cyroam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PortalScreenActivity extends AppCompatActivity {

    private Button friendButton;         // define login button variable
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_screen);

        friendButton = findViewById(R.id.Friendsbutton);    // L
        exitButton = findViewById(R.id.ExitButton);

        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When friend button is clicked it takes you to the friend screen
                Intent intent = new Intent(PortalScreenActivity.this, FriendsActivity.class);
                startActivity(intent);
            }

        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when exit button is pressed takes you back to the Maps Activity
                Intent intent = new Intent(PortalScreenActivity.this, MapsActivity.class);
                startActivity(intent);
            }

        });
    }
}