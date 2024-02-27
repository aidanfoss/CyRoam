package com.lg1_1.cyroam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.lg1_1.cyroam.util.Pin;

public class NewPinActivity extends AppCompatActivity {

    private EditText latitudeText;
    private EditText longitudeText;
    private EditText nameText;

    private Button newPinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pin);

        /* initialize UI elements */
        longitudeText = findViewById(R.id.longitude_edt);
        latitudeText = findViewById(R.id.latitude_edt);
        nameText = findViewById(R.id.pin_name_edt);
        newPinButton = findViewById(R.id.new_pin_btn);    // link to login button in the Login activity XML

        newPinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO make the coordinates grab from the device rather than manual input.
                /* grab coordinates from user inputs */
                double longitudeIn = longitudeText.getText().length();
                double latitudeIn = latitudeText.getText().length();
                String name = nameText.getText().toString();

                //when new pin is created, send all relevant information to the maps activity
                //todo maybe pass pin object over to the maps activity, rather than the seperate information

                Pin newPin = new Pin(longitudeIn,latitudeIn,name);

                Intent intent = new Intent(NewPinActivity.this, MapsActivity.class);
                intent.putExtra("LONGITUDE", latitudeIn);
                intent.putExtra("LATITUDE", longitudeIn);
                intent.putExtra("NAME", name);

                //intent.putExtra("PIN", newPin);

                startActivity(intent);  // go to MainActivity with the key-value data
            }
        });
    }


}
