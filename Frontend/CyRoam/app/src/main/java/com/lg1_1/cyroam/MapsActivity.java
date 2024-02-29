package com.lg1_1.cyroam;

import com.lg1_1.cyroam.util.Pin;
import static com.lg1_1.cyroam.MainActivity.url;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.Manifest;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Objects;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private RequestQueue mQueue; // define volley request queue
    private FloatingActionButton newPinButton; // define new pin button variable
    private FloatingActionButton discoverButton; //define discoverButton

    //TODO define user object here to determine what they can and cant do
    //this can also change what does and doest display (ex:no fog on admin account, no distance limit etc)

    //location & locationPermissions things
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    private Location lastKnownLocation;
    private Boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    GoogleMap gMap;
    FrameLayout map;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); //Link to XML

        mQueue = Volley.newRequestQueue(this); //defines volley queue for fillPinVector
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }


        //define and implement buttons
        newPinButton = findViewById(R.id.newPinButton);
        discoverButton = findViewById(R.id.discoverButton);
        newPinButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, NewPinActivity.class);
            startActivity(intent);  // go to NewPinActivity
        });
        discoverButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, ProgressActivity.class);
            startActivity(intent);  // go to NewPinActivity
        });

        map = findViewById(R.id.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }




    //https://github.com/googlemaps-samples/android-samples/blob/588287af6ea872c6098c2b4c727503200de4dc7e/tutorials/java/CurrentPlaceDetailsOnMap/app/src/main/java/com/example/currentplacedetailsonmap/MapsActivityCurrentPlace.java#L267-L282


    private void updateLocationUI() { //got this chunk of code from https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial#java_4
        if (gMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                gMap.setMyLocationEnabled(true);
                gMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                gMap.setMyLocationEnabled(false);
                gMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", Objects.requireNonNull(e.getMessage()));
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;

        Bundle extras = getIntent().getExtras(); //call that checks for passed information
        if(extras == null) {
            Log.w("extras", "missing any passed information");

        } else {
            Log.w("extras", "extras != null");
            //create new pin with passed data //pinVector.add(new Pin(extras.getDouble("LATITUDE"),extras.getDouble("LONGITUDE"), (extras.getString("NAME") + "( " + extras.getDouble("LATITUDE") + ", " + extras.getDouble("LONGITUDE") + ")")));
            Pin newPin = new Pin (extras.getDouble("LATITUDE"),extras.getDouble("LONGITUDE"),extras.getString("NAME"),extras.getInt("PINID"));
            Marker newMarker = this.gMap.addMarker(new MarkerOptions().position(newPin.getPos()).title(newPin.getName()));
        }
        fillPinVector();


        this.gMap.moveCamera(CameraUpdateFactory.zoomTo(13));
//        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(pinVector.elementAt(1).getPos()));


        Pin zeroZeroPin = new Pin(0.000,0.005,"Zero Zero Hardcoded pin");
        Marker zeroZero = this.gMap.addMarker(new MarkerOptions().position(zeroZeroPin.getPos()).title(zeroZeroPin.getName()));
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(zeroZeroPin.getPos()));
    }

    /*
    Function that makes a volley request to recieve all pin data. Uses url from MainActivity, and uses the
    googleMaps gMap declaration from the top of the class.
    */


    private void fillPinVector(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url+"/pins", null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("pins");
                        //JSONObject jsonArray = response.getJSONObject("pins");
                        Log.w("volley", "request success");

                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject pin = jsonArray.getJSONObject(i);

                            int id = pin.getInt("id");
                            double x = pin.getDouble("x");
                            double y = pin.getDouble("y");
                            String name = pin.getString("name");

//                            pinVector.add(new Pin(x,y,name,id));
                            Log.w("volley", "pinVector added " + name);
//                            Log.w("volley", "pinVector size: " + String.valueOf(pinVector.size()));
                            Pin newPin = new Pin(x,y,name,id);

                            this.gMap.addMarker(new MarkerOptions().position(newPin.getPos()).title(newPin.getName()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        mQueue.add(request);
    }
}

