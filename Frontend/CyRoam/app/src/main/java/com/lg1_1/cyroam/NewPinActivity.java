package com.lg1_1.cyroam;

import static com.lg1_1.cyroam.MainActivity.url;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lg1_1.cyroam.util.Pin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class NewPinActivity extends AppCompatActivity {

    private RequestQueue mQueue; // define volley request queue
    private final String url = MainActivity.url; // get URL from main activity
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
                double longitudeIn = Double.parseDouble(String.valueOf(longitudeText.getText()));
                double latitudeIn = Double.parseDouble(String.valueOf(latitudeText.getText()));
                String name = nameText.getText().toString();

                //todo put post here
                //send x,y,name in one

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

    /*
    This is public for now because it might be useful later in the future
    (like making the new pin button not be a new activity)
     */
    public void postPin(Pin pin){
        RequestQueue mQueue = Volley.newRequestQueue(this);
        //https://stackoverflow.com/questions/46171093/how-to-use-post-request-in-android-volley-library-with-params-and-header
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject respObj = new JSONObject(String.valueOf(response));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e){
                e.printStackTrace();
            }
        });
    }
}
