package com.example.androidexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    FrameLayout map;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        map = findViewById(R.id.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;
        updateLocationUI();
        LatLng mapIowaState = new LatLng(42.023949, -93.647595);
        this.gMap.addMarker(new MarkerOptions().position(mapIowaState).title("Marker At College"));
        this.gMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(mapIowaState));
//        this.gMap.


//this doesnt belong INSIDE onMapReady, but it needs to call gMap, so i need to figure out how to use gMap
// outside of the onMapReady function
        private void updateLocationUI() { //got this chunk of code from https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial#java_4
            //changed map call to gMap call, not sure how to check for locationPermissionGranted or lastKnownLocation just yet.
            //did i miss something in the documentation? This entire function is in the wrong spot.
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
    }


}