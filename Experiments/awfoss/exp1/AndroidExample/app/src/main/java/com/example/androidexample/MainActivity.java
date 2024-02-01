package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

//import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;   // define message textview variable
    private Button zachButton;
   // @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML
        String name = "Aidan";
        /* initialize UI elements */
        messageText = findViewById(R.id.main_msg_txt);      // link to message textview in the Main activity XML
        //for (int i = 0; i < 100; i++){ adds all 100 ".'s" before ever displaying
        //while(true){ never displays text
            name += "'s Lab from last semester";
            messageText.setText(name);
            /*
            try {
                wait(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            */

        //}
        zachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when zach button is pressed, use intent to switch to ZachM Activity */
                Intent intent = new Intent(MainActivity.this, ZachMActivity.class);
                startActivity(intent);
            }
        });

    }
}