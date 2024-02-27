package com.lg1_1.cyroam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lg1_1.cyroam.util.Pin;

import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private FloatingActionButton newPinButton;        // define new pin button variable

    //TODO establish user object to determine what they can and cant do
    //this can also change what does and doest display (ex:no fog on admin account, no distance limit etc)
    //TODO establish pointOfInterest object to allow easier passing of pins
    //(lat, long, name, desc, unique image??, etc)

    GoogleMap gMap;
    FrameLayout map;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        newPinButton = findViewById(R.id.newPinButton);
        newPinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(MapsActivity.this, NewPinActivity.class);
                startActivity(intent);  // go to NewPinActivity
            }
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
//    } //This is borked atm. Not sure how to check for locationPermissionGranted yet.

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;
        //updateLocationUI();
        //const image = R.drawable.qMark;
        //the following code can be placed into its own class? or whatever its called.
        // want to be able to call New POI (lat, long, name, description, reward, etc)
        //then we can make a standalone temporary app to allow us to post, put, recieve location data
        //to establish our database of points of interest. Make dozens of the following with a few lines and a for loop.
        //LatLng mapIowaState = new LatLng(42.023949, -93.647595);

        Bundle extras = getIntent().getExtras();

        Pin ifStatement; //declare pin to fill with values below
        if(extras == null) {
            //(pins.Update() or something)
            //(retrieve all pins from database. maybe later, make this check your location and only recieve ones within a certain distance.

            //this hard-coded pin will only show on the first load. Change this later by calling it from the server, where it should be hard coded.
            ifStatement = new Pin(42.023949,-93.647595, "Test Pin");

        } else {
            //create new pin given the data that was passed
            ifStatement = new Pin(extras.getDouble("LATITUDE"),extras.getDouble("LONGITUDE"), extras.getString("NAME"));
            //then, call all previously generated pins. (pins.Update or something)
            //(THESE MIGHT END UP BEING THE SAME THING. CREATE NEW PIN -> SEND TO SERVER -> RECIEVE ALL PINS, INCLUDING NEW ONE)
        }

        //create pin based on information given in the if statement above. Change this later to only activate when a new pin is created.
        Marker ifStatementMarker = this.gMap.addMarker(new MarkerOptions().position(ifStatement.getPos()).title(ifStatement.getName()));//.icon(R.drawable.qMark));
        this.gMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(ifStatement.getPos()));

        Pin zeroZeroPin = new Pin(0.000,0.005,"Zero Zero");
        Marker zeroZero = this.gMap.addMarker(new MarkerOptions().position(zeroZeroPin.getPos()).title(zeroZeroPin.getName()));

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
}

/*
TODO create callAllPins function, which will call all pins from database. (recursive?)
for now, just hardcode a single pin for calling from database. (Library)
Pin Library = new Pin(42.023949,-93.647595, "Library");
to
Pin new = new Pin(database.x(1), database.y(1), database.name(1))

for (int i = 0; i <= database.Length(); i++){
    Pin newPin = new Pin(database.x(1), database.y(1), database.name(1), database.ID(1));
    Marker newMarker = this.gMap.addMarker(new MarkerOptions().position(newPin.getPos()).title(newPin.getName()));//.icon(R.drawable.qMark));
}

*/

