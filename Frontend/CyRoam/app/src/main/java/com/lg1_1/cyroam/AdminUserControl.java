package com.lg1_1.cyroam;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AdminUserControl extends AppCompatActivity {
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
    private Button promotebutton;
    /**
     * Button that Bans user
     */
    private Button banbutton;
    /**
     * holds the username from text edit to add
     */
    private EditText usernameEditText;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_admin_user_control);
        backbutton = findViewById(R.id.ReturnAdminButtonC);
        banbutton =  findViewById(R.id.adminc_BAN_btn);
        promotebutton = findViewById(R.id.Promote_btn);
        usernameEditText = findViewById(R.id.admin2_username_edt);
    }
}