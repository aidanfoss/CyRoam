package com.lg1_1.cyroam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FriendActivity extends AppCompatActivity {
    private Button backbutton;

    private Button friendsearch;

    private boolean found = false;

    private TextView outputtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        backbutton = findViewById(R.id.Backbutton);
        friendsearch =  findViewById(R.id.searchButton);
        outputtext = findViewById(R.id.Outputtext);


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(FriendActivity.this, MainActivity.class);
                startActivity(intent);  // go to SignupActivity
            }
        });
        friendsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* when signup button is pressed, use intent to switch to Signup Activity */
                if(found = false){
                    outputtext.setText("User doesnot exist");
                }
                else{
                    outputtext.setText("Invite sent");
                }
            }
        });
    }
}