package com.lg1_1.cyroam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import com.lg1_1.cyroam.util.Pin;
import com.lg1_1.cyroam.volley.pinVolley;
import com.lg1_1.cyroam.websockets.WebSocketManager;
import com.lg1_1.cyroam.websockets.aidanWebSocket;

/**
 * Activity that creates a new pin using user inputs, then stores it in the database.
 * @author Aidan Foss
 */
public class NewPinActivity extends AppCompatActivity {
    private final String TAG = "CreatePin";
    private RequestQueue mQueue; // define volley request queue
    private final String url = MainActivity.url; // get URL from main activity
    private EditText latitudeText;
    private EditText longitudeText;
    private EditText nameText;
    private Button newPinButton;

    private pinVolley volley; //init pinVolley class
    private aidanWebSocket webSocket;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     *     initializes UI and defines volley variables
     *     gets bundle information, and displays current
     *     location on the text boxes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pin);

        /* initialize UI elements */
        longitudeText = findViewById(R.id.longitude_edt);
        latitudeText = findViewById(R.id.latitude_edt);
        nameText = findViewById(R.id.pin_name_edt);
        newPinButton = findViewById(R.id.new_pin_btn);    // link to login button in the Login activity XML

        volley = new pinVolley(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.i(TAG, "extras, missing any passed information");
            //make debug user, likely name aaa, password bbb, any relevant info
        } else {
            Log.i(TAG, "extras != null");

            longitudeText.setText(String.valueOf(extras.getDouble("lng")));
            latitudeText.setText(String.valueOf(extras.getDouble("lat")));
            nameText.setText("test");
        }



        newPinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO make the coordinates grab from the device rather than manual input.
                /* grab coordinates from user inputs */
                double longitudeIn = Double.parseDouble(String.valueOf(longitudeText.getText()));
                double latitudeIn = Double.parseDouble(String.valueOf(latitudeText.getText()));
                String name = nameText.getText().toString();

                //todo put post here
                volley.createPin(latitudeIn, longitudeIn, name, new pinVolley.CreatePinCallback() {
                    @Override
                    public void onSuccess(int idSuccess) {
                        Intent intent = new Intent(NewPinActivity.this, MapsActivity.class);
                        intent.putExtra("LONGITUDE", latitudeIn);
                        intent.putExtra("LATITUDE", longitudeIn);
                        intent.putExtra("NAME", name);
                        intent.putExtra("PINID", idSuccess);
                        Log.d(TAG, String.valueOf(idSuccess));

                        Log.v(TAG, "Starting websocket send");
                        Log.d(TAG, "CreatePin Success!");

                        if (WebSocketManager.getInstance().isConnected()) {
                            WebSocketManager.getInstance().sendAidan(String.valueOf(idSuccess));
                        }
                        finish();
                    }
                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e(TAG, "CreatePin Failed! " + errorMessage);
                    }
                });

                Pin newPin = new Pin(longitudeIn,latitudeIn,name);

                //todo add websocket broadcast


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
//    public void postPin(Pin pin){
//        RequestQueue mQueue = Volley.newRequestQueue(this);
//        //https://stackoverflow.com/questions/46171093/how-to-use-post-request-in-android-volley-library-with-params-and-header
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>(){
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONObject respObj = new JSONObject(String.valueOf(response));
//
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError e){
//                e.printStackTrace();
//            }
//        });
//    }
}
