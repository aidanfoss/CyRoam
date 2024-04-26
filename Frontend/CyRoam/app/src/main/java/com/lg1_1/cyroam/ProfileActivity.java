package com.lg1_1.cyroam;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        //ConstraintLayout constraintLayout = findViewById(R.id.mainlayout);
        //AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        //animationDrawable.setEnterFadeDuration(2500);
        //animationDrawable.setExitFadeDuration(5000);
        //animationDrawable.start();
        //backButton = findViewById(R.id.backButtonProfileScreen);
        //scoreOutput = findViewById(R.id.textViewScoreOutput);
        //pinOutput = findViewById(R.id.textViewPinsCount);


    }
}