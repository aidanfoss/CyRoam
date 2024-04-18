package com.lg1_1.cyroam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
/**
 * Is esentially the middle man screen that has buttons that
 * @deprecated
 * take you to the next screen
 * @author Nicholas Kirschbaum
 */
public class PortalScreenActivity extends AppCompatActivity {
    /**
     * @author Nicholas Kirschbaum
     * Friend button takes to friend screen
     *
     */
    private Button friendButton;         // define login button variable
    /**
     * @author Nicholas Kirschbaum
     * exit button takes(back) to Map screen
     *
     */
    private Button exitButton;

    private Button addButton;

    private Button progress;
    private Button leaderButton;

    private Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_screen);

        friendButton = findViewById(R.id.Friendsbutton);
        leaderButton = findViewById(R.id.Leaderboardbutton);// L
        exitButton = findViewById(R.id.ExitButton);
        addButton = findViewById(R.id.trophyButton);
        progress = findViewById(R.id.progressButton);
        settings = findViewById(R.id.settingsButton);
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
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when exit button is pressed takes you back to the Maps Activity
                Intent intent = new Intent(PortalScreenActivity.this, AddFriends.class);
                startActivity(intent);
            }

        });
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when exit button is pressed takes you back to the Maps Activity
                Intent intent = new Intent(PortalScreenActivity.this, ProfileActivity.class);
                startActivity(intent);
            }

        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when exit button is pressed takes you back to the Maps Activity
                Intent intent = new Intent(PortalScreenActivity.this, SettingsActivity.class);
                startActivity(intent);
            }

        });
        leaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when exit button is pressed takes you back to the Maps Activity
                Intent intent = new Intent(PortalScreenActivity.this, LeaderBoard.class);
                startActivity(intent);
            }

        });
    }
}