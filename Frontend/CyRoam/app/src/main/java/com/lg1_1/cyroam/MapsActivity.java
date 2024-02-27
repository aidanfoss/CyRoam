package com.lg1_1.cyroam;

import static com.lg1_1.cyroam.MainActivity.url;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lg1_1.cyroam.util.Pin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //private final String url = MainActivity.url; // get URL from main activity (might be able to just call it each time individually instead of defining it here)
    private RequestQueue mQueue; // define volley request queue
    private FloatingActionButton newPinButton; // define new pin button variable

    Vector<Pin> pinVector = new Vector<>();


    //TODO define user object here to determine what they can and cant do
    //this can also change what does and doest display (ex:no fog on admin account, no distance limit etc)

    GoogleMap gMap;
    FrameLayout map;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); //Link to XML

        mQueue = Volley.newRequestQueue(this);
        newPinButton = findViewById(R.id.newPinButton);
        newPinButton.setOnClickListener(v -> {
            /* when signup button is pressed, use intent to switch to Signup Activity */
            Intent intent = new Intent(MapsActivity.this, NewPinActivity.class);
            startActivity(intent);  // go to NewPinActivity
        });

        map = findViewById(R.id.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }




    //https://github.com/googlemaps-samples/android-samples/blob/588287af6ea872c6098c2b4c727503200de4dc7e/tutorials/java/CurrentPlaceDetailsOnMap/app/src/main/java/com/example/currentplacedetailsonmap/MapsActivityCurrentPlace.java#L267-L282


//    private void updateLocationUI() { //got this chunk of code from https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial#java_4
//        //changed map call to gMap call, not sure how to check for locationPermissionGranted or lastKnownLocation just yet.
//        //did i miss something in the documentation? This entire function is in the wrong spot.
//        if (gMap == null) {
//            return;
//        }
//        try {
//            boolean locationPermissionGranted = true; //temp solution?
//            if (locationPermissionGranted) {
//                gMap.setMyLocationEnabled(true);
//                gMap.getUiSettings().setMyLocationButtonEnabled(true);
//            } else {
//                gMap.setMyLocationEnabled(false);
//                gMap.getUiSettings().setMyLocationButtonEnabled(false);
//                lastKnownLocation = null;
//                getLocationPermission();
//            }
//        } catch (SecurityException e)  {
//            Log.e("Exception: %s", Objects.requireNonNull(e.getMessage()));
//        }
//    } //This is fucked atm. Not sure how to check for locationPermissionGranted yet.

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;

        Bundle extras = getIntent().getExtras(); //call that checks for passed information
        if(extras == null) {
            Log.w("volley", "name == null");

        } else {
            Log.w("volley", "extras != null");
            //create new pin with passed data //pinVector.add(new Pin(extras.getDouble("LATITUDE"),extras.getDouble("LONGITUDE"), (extras.getString("NAME") + "( " + extras.getDouble("LATITUDE") + ", " + extras.getDouble("LONGITUDE") + ")")));
        }
        Log.w("volley, marker", "pre-fill pinVector.size() = " + String.valueOf(pinVector.size()));
        fillPinVector(pinVector);
        while(pinVector.size() < 1){};
        Log.w("volley, marker", "post-fill pinVector.size() = " + String.valueOf(pinVector.size()));
        for (int i = 0; i < pinVector.size(); i++){
            MarkerOptions place = new MarkerOptions().position(pinVector.elementAt(i).getPos()).title(pinVector.elementAt(i).getName());
            gMap.addMarker(place);
            Log.w("marker", "created new Marker: ( " + String.valueOf(pinVector.elementAt(i).getLat()) + ", " + String.valueOf(pinVector.elementAt(i).getLong()) + ", " + pinVector.elementAt(i).getName());
            this.gMap.moveCamera(CameraUpdateFactory.newLatLng(pinVector.elementAt(i).getPos()));
//          this.gMap.addMarker(new MarkerOptions().position(pinVector.elementAt(i).getPos()).title(pinVector.elementAt(i).getName()));
        }



        //create pin based on information given in the if statement above. Change this later to only activate when a new pin is created.
        //Marker ifStatementMarker = this.gMap.addMarker(new MarkerOptions().position(pinVector.lastElement().getPos()).title(pinVector.lastElement().getName()));//.icon(R.drawable.qMark));
        this.gMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        //this.gMap.moveCamera(CameraUpdateFactory.newLatLng(pinVector.lastElement().getPos()));
        if (pinVector.size() > 1) {
            this.gMap.moveCamera(CameraUpdateFactory.newLatLng(pinVector.elementAt(1).getPos()));
            Log.w("volley", "inside if statement: pinVector<1> = " + pinVector.elementAt(1).getName());
        }
        Log.w("volley", "outside if statement: pinVector.size() = " + String.valueOf(pinVector.size()));

        Pin zeroZeroPin = new Pin(0.000,0.005,"Zero Zero");
        Marker zeroZero = this.gMap.addMarker(new MarkerOptions().position(zeroZeroPin.getPos()).title(zeroZeroPin.getName()));
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(zeroZeroPin.getPos()));
        /*
        shitty hardcoded pin call, delete when no longer needed for copy-paste
        Pin IowaState = new Pin(42.023949,-93.647595, "Iowa State Campus");
        Marker midCampus = this.gMap.addMarker(new MarkerOptions().position(IowaState.getPos()).title(IowaState.getName()));//.icon(R.drawable.qMark));
        this.gMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(IowaState.getPos()));
        */





        //replace this with a way to call all locations off the database and establish them as new LatLng's
        /* GENERIC PSEUDOCODE (not fully thought through)
        for (int i = 0; i < (Get Num of Locations); i++) {

            NEW POI (int i)
            addmarker i
        }
        this.gMap.moveCamera(CameraUpdateFactory.zoomTo(13)); //13 is a generic zoom, worked well with testing, not sure what a changed zoom variable would mean
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(CURRENTLOCATION)); //fix CURRENTLOCATION later
        */




    }
    private void fillPinVector(Vector<Pin> pinVector){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("pins");
                        //JSONObject jsonArray = response.getJSONObject("pins");
                        Log.w("volley", "request success");

                        for(int i =0; i < jsonArray.length(); i++){
                            JSONObject pin = jsonArray.getJSONObject(i);

                            int id = pin.getInt("id");
                            double x = pin.getDouble("x");
                            double y = pin.getDouble("y");
                            String name = pin.getString("name");

                            pinVector.add(new Pin(x,y,name,id));
                            Log.w("volley", "pinVector added " + name);
                            Log.w("volley", "pinVector size: " + String.valueOf(pinVector.size()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        mQueue.add(request);
    }
}

