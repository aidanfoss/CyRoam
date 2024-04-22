package com.lg1_1.cyroam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AdminLeaderBoardControl extends AppCompatActivity {
    /**
     * tag for debugging
     */
    private String TAG = "AdminLeaderBoardControl";
    /**
     *
     * Takes url from Main
     */
    private String mainURL = MainActivity.url;


    /**
     * Is the back button off the screen
     */
    private Button backbutton;
    /**
     * Is a button that takes user
     */
    private Button UserSearch;
    /**
     * Button that Bans user
     */
    private Button banbutton;
    /**
     * holds the username from text edit to add
     */
    private EditText usernameEditText;
    /**
     * holds the score from text edit to add
     */
    private EditText scoreEditText;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_admin_leader_board_control);
        //backbutton = findViewById(R.id.ReturnAdminButton);
        UserSearch =  findViewById(R.id.adminscorebutton);
        banbutton = findViewById(R.id.bannedbutton);
        usernameEditText = findViewById(R.id.signup_username_edt);
        scoreEditText = findViewById(R.id.emailthe43t1st);
        /*
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* when signup button is pressed, use intent to switch to Signup Activity
                Intent intent = new Intent(AdminLeaderBoardControl.this, MainActivity.class);
                startActivity(intent);  // go to SignupActivity
            }
        });

                 */
        UserSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        banbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}