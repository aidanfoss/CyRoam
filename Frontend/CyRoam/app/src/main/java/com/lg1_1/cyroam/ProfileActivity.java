package com.lg1_1.cyroam;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * This shows the Users basic information and basic achievements
 * @author Nicholas Kirschbaum
 */
public class ProfileActivity extends AppCompatActivity {
    private TextView scoreOutput;

    private TextView pinOutput;

    private Button backButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ConstraintLayout constraintLayout = findViewById(R.id.mainlayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        backButton = findViewById(R.id.backButtonProfileScreen);
        scoreOutput = findViewById(R.id.textViewScoreOutput);
        pinOutput = findViewById(R.id.textViewPinsCount);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, PortalScreenActivity.class);
                startActivity(intent);
            }


        });

    }
}