package com.lg1_1.cyroam;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;
import static com.lg1_1.cyroam.MainActivity.url;
import static com.lg1_1.cyroam.MainActivity.wsurl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lg1_1.cyroam.util.Pin;
import com.google.android.gms.location.LocationRequest;
import com.lg1_1.cyroam.util.User;
import com.lg1_1.cyroam.volley.pinVolley;
import com.lg1_1.cyroam.volley.friendVolley;
import com.lg1_1.cyroam.volley.progressVolley;
import com.lg1_1.cyroam.websockets.WebSocketListener;
import com.lg1_1.cyroam.websockets.aidanWebSocket;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.PingFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Objects;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, WebSocketListener {
    private final String TAG = "MapsActivityTag"; //debugging tag


    //define UI
    private FloatingActionButton newPinButton; // define new pin button variable
    private FloatingActionButton discoverButton; //define discoverButton
    private TextView textView;
    private BitmapDescriptor smallUndiscoveredIcon;
    private Bitmap smallUndiscovered;


    //define volley classes and requestQueue
    private pinVolley pinVolley;

    private friendVolley friendVolley;
    private progressVolley progressVolley;
    private User username;
    private RequestQueue mQueue; // define volley request queue

    //TODO define user object here to determine what they can and cant do
    //this can also change what does and doest display (ex:no fog on admin account, no distance limit etc)

    //location & locationPermissions things
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    //    private Location lastKnownLocation;
//    private Boolean locationPermissionGranted;
//    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private double lat = 0;
    private double lng = 0;


    GoogleMap gMap;
    FrameLayout map;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); //Link to XML

        //Nick, todo: define your websocket class here, similar to mine
        aidanWebSocket aidanClient;

        //define icons as BitmapDescriptors for the .icon call in Marker Declarations
        smallUndiscovered = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.undiscovered), 128, 128, false);
        smallUndiscoveredIcon = BitmapDescriptorFactory.fromBitmap(smallUndiscovered);

        this.pinVolley = new pinVolley(this); //defines pinVolley class
        this.progressVolley = new progressVolley(this); //defines progressVolley class

        mQueue = Volley.newRequestQueue(this); //defines volley queue for fillMap
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, 1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(500)
                .setMaxUpdateDelayMillis(1000)
                .build(); //todo repair and use the geolocation code

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        //define and implement UI
        map = findViewById(R.id.map); //defines the map in the UI
        textView = findViewById(R.id.textView2); //defines debug text screen
        newPinButton = findViewById(R.id.newPinButton);
        discoverButton = findViewById(R.id.discoverButton);
        newPinButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, NewPinActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            startActivity(intent);  // go to NewPinActivity
        });
        discoverButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, ProgressActivity.class);
            startActivity(intent);  // go to NewPinActivity
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        // Initialize location callback to receive location updates
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    //Log.w(TAG, String.valueOf(location.getLatitude() + "," + location.getLongitude()));
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        };

        //Make Websocket Connection
        try {
            Log.v("Aidan " + TAG, "trying websocket connection");
            aidanClient = new aidanWebSocket(wsurl, this);
            aidanClient.connect();
            //@Nick, todo: add your websocket try here too. Hopefully that works.
            //You could also make a similar try/catch right below this one, might be a better idea
        } catch (URISyntaxException e) {
            Log.e(TAG, "Websocket Error: " + e.toString());
            Log.e("Aidan WebSocket", "Websocket Error: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override //This gets called when the map initializes. It only happens once, but its seperate from onCreate.
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;
        gMap.getUiSettings().setAllGesturesEnabled(false); //disables being able to move camera around
        this.gMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        gMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);

        fillMap(); //fills the map with relevant information


        //Following statement and if/else check for user information or any passed pin information.
        //Its in onMap because of the pin information
        Bundle extras = getIntent().getExtras(); //call that checks for passed information
        if (extras == null) {
            Log.i(TAG, "extras, missing any passed information");
            //make debug user, likely name aaa, password bbb, any relevant info
        } else {
            Log.i(TAG, "extras != null");
            //create new pin with passed data //pinVector.add(new Pin(extras.getDouble("LATITUDE"),extras.getDouble("LONGITUDE"), (extras.getString("NAME") + "( " + extras.getDouble("LATITUDE") + ", " + extras.getDouble("LONGITUDE") + ")")));
//            Pin newPin = new Pin (extras.getDouble("LATITUDE"),extras.getDouble("LONGITUDE"),extras.getString("NAME"),extras.getInt("PINID"));
//            Marker newMarker = this.gMap.addMarker(new MarkerOptions().position(newPin.getPos()).title(newPin.getName()));
            if (extras.containsKey("discovered")) { //discover response
                textView.append("\n Pin with ID " + extras.getInt("pinId") + " discovered: " + String.valueOf(extras.getBoolean("discovered")));
                progressVolley.fetchProgress(extras.getInt("pinId"), new progressVolley.VolleyCallbackGet() {
                    @Override
                    public void onSuccess(int pinId, int userId, boolean discovered, int progressObjId) {
                        Log.d(TAG, "Progress Get Req: " + String.valueOf(pinId) + " " + String.valueOf(userId) + " " + String.valueOf(discovered));
                        textView.append("\nProgress Data Received Via Volley Get Request: " + String.valueOf(pinId) + " " + String.valueOf(userId) + " " + String.valueOf(discovered));
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e(TAG, "fetchProgressData error: " + errorMessage);
                    }
                });
            }
            if (extras.containsKey("LATITUDE")) { //newpin response
                textView.append("\n New Pin Created with values: (" + extras.getString("NAME") + ", " + extras.getDouble("LATITUDE") + ", " + extras.getDouble("LONGITUDE") + ")");
            }
            if (extras.containsKey("PINID")) {
                //GET REQUEST
                int pinID = extras.getInt("PINID");
                pinVolley.fetchPinData(pinID, new pinVolley.FetchPinCallback() {
                    @Override
                    public void onSuccess(Pin pin) {
                        textView.append("\nPin Data Received Via Volley Get Request: " + pin.getDebugDescription());
                        Log.d(TAG, "Pin Get Req: " + pin.getDebugDescription());
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        textView.append("\n get request failed for pin ID " + String.valueOf(pinID));
                        Log.e(TAG, "fetchPinData error: " + errorMessage);
                    }
                });
            }
            if (extras.containsKey("LoginSuccess")) {
                textView.append("\n Login with value (" + extras.getBoolean("LoginSuccess") + ")");
            }
        } //Todo repurpose this block of code to receive login information from nick


    }


    private void fillMap() {
        /*
        Function that makes a volley request to recieve all pin data.
        Uses url from MainActivity, and uses the googleMaps gMap declaration
        from the top of the class.

        only called pinVector because it used to use a vector. can change later.
        */
        Log.v(TAG, "fillMap() called");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + "/pins", null,
                response -> {
                    try {
                        JSONArray jsonArray = response;
                        //JSONObject jsonArray = response.getJSONObject("pins");
                        Log.d(TAG + "volley", "request success");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject pin = jsonArray.getJSONObject(i);

                            int id = pin.getInt("id");
                            double x = pin.getDouble("x");
                            double y = pin.getDouble("y");
                            String name = pin.getString("name");
                            String description = "temporary description";
                            boolean discoveredOut = false;

                            Pin newPin = new Pin(x, y, name, description, id, discoveredOut);
                            Log.d(TAG + "discover" + "volley", "pin created " + newPin.getDebugDescription());
                            if (newPin.isDiscovered()) {
                                Log.v(TAG + "discover", "discovered pin created: " + newPin.getDebugDescription());
                                Marker marker = this.gMap.addMarker(new MarkerOptions()
                                        .position(newPin.getPos())
                                        .title(newPin.getName())
                                        .snippet(newPin.getDescription())
                                        .icon(BitmapDescriptorFactory.defaultMarker())
                                );
                                marker.setTag(newPin);
                            } else {
                                Log.v(TAG + "discover", "undiscovered pin created: " + newPin.getDebugDescription());
                                Marker marker = this.gMap.addMarker(new MarkerOptions()
                                        .position(newPin.getPos())
                                        .title(newPin.getName())
                                        .snippet(newPin.getDescription())
                                        .icon(smallUndiscoveredIcon)
                                );
                                marker.setIcon(smallUndiscoveredIcon);
                                Log.e(TAG + "discover", "test");
                                marker.setTag(newPin);
                            }
                        }

                        textView.setText(textView.getText() + "\nCreatedPins");

                    } catch (JSONException e) {
                        Log.e(TAG + "volley", "JSONException: " + e.toString());
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        mQueue.add(request);
    } //fills map with markers, creates pin objects. adds pin object to tags of marker for onclick listener

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    //handles click on marker. Uses pin object inside each pins tag
    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        marker.setIcon(BitmapDescriptorFactory.defaultMarker());
        Pin clickPin = (Pin) Objects.requireNonNull(marker.getTag());
        ((Pin) Objects.requireNonNull(marker.getTag())).setTrue(); //sets discovery in the pin object inside the tag
        //use clickPin information to send discovery information if relevant.

        clickPin.getID();
        return false;
    }

    @Override
    public void onPinRecieved(int wsPinIdInput) {
        pinVolley.fetchPinData(wsPinIdInput, new pinVolley.FetchPinCallback() {
            @Override
            public void onSuccess(Pin pin) {
                textView.append("\nPin Data Received Via WebSocket + Volley: " + pin.getDebugDescription());
                Log.d("Aidan "+ TAG + " Volley Websocket", "Pin Get Req: " + pin.getDebugDescription());
            }

            @Override
            public void onFailure(String errorMessage) {
                textView.append("\n get request failed for pin ID " + String.valueOf(wsPinIdInput));
                Log.e(TAG, "fetchPinData error: " + errorMessage);
            }
        });
    }
    @Override
    public void onfredReqRecieved(String name) {
        //todo nick put it here
        //likely just edit the text box,
        // similar to mine above using textView.append()
        //you can change the passed information. Right now, its just ID
        //youd have to change it here, in the webSocketListener, and in nickWebSocket


        friendVolley.addfriendsReq(name, username, new friendVolley.addFriendCallback() {


            @Override
            public void onSuccess(User user) {
                textView.append("\nThis user was added as a friend");
                Log.d("Nick "+ TAG + " Volley Websocket", "FriendAdd Get Req: ");
            }

            @Override
            public void onFailure(String errorMessage) {
                textView.append("\nThis user was unable to be added as a friend");
                Log.e(TAG, "fetchuseraddData error: " + errorMessage);
            }
        });

    }
}