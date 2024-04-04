package com.lg1_1.cyroam;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;
import static com.lg1_1.cyroam.MainActivity.url;

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
import com.google.android.gms.location.LocationRequest;
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
import com.lg1_1.cyroam.aidansActivities.PinInformationActivity;
import com.lg1_1.cyroam.util.Pin;
import com.lg1_1.cyroam.util.User;
import com.lg1_1.cyroam.volley.friendVolley;
import com.lg1_1.cyroam.volley.pinVolley;
import com.lg1_1.cyroam.volley.progressVolley;
import com.lg1_1.cyroam.websockets.WebSocketListener;
import com.lg1_1.cyroam.websockets.WebSocketManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Hub Activity; displays map, pins, info, friend requests and stores most info
 * @author Aidan Foss
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, WebSocketListener {
    private final String TAG = "MapsActivityTag"; //debugging tag

    private Bundle extras;
    private User user;

    private TextView textView;
    private BitmapDescriptor smallUndiscoveredIcon;


    //define volley classes and requestQueue
    private pinVolley pinVolley;

    private friendVolley friendVolley;

    private AddFriends addFriends;
    private progressVolley progressVolley;
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

    /**
     * Does multiple things on opening of this activity
     *      -creates websocket manager and attempts connection
     *      -creates bitmaps for display on pins
     *      -creates volley queues for volley requests in the rest of the class
     *      -creates volley classes for helper use in the rest of the class
     *      -begins gathering location data.
     *      -requests location data permissions if they are missing
     *      -defines gmap and displays it
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); //Link to XML

        //gather extras
        extras = getIntent().getExtras();
        if (extras.containsKey("username")){
            user = new User(
                    extras.getString("username"),
                    extras.getString("password"),
                    extras.getInt("userID"));
        } else if (!extras.containsKey("username")){
            user = new User("failSafe", "failSafe", -1);
            Log.w(TAG + "USERINFO", "Invalid passed information, using failSafe user");
        }

        //initialize websocketManager
        try {
            Log.v(TAG, "onCreate Websocket Try");
            WebSocketManager.getInstance().openWebSocketConnection(user.getUsername(), this);
        } catch (URISyntaxException e) {
            Log.w(TAG, "onCreate WebSocket Fail");
            throw new RuntimeException(e);
        }

        //define icons as BitmapDescriptors for the .icon call in Marker Declarations
        Bitmap smallUndiscovered = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.undiscovered), 128, 128, false);
        smallUndiscoveredIcon = BitmapDescriptorFactory.fromBitmap(smallUndiscovered);

        this.pinVolley = new pinVolley(this); //defines pinVolley class
        this.progressVolley = new progressVolley(this); //defines progressVolley class
        this.friendVolley = new friendVolley(this); //defines nicks friendVolley

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
        //define UI
        // define new pin button variable
        FloatingActionButton newPinButton = findViewById(R.id.newPinButton);
        //define discoverButton
        FloatingActionButton discoverButton = findViewById(R.id.discoverButton);
        newPinButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, NewPinActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            startActivity(intent);  // go to NewPinActivity
        });
        discoverButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, PortalScreenActivity.class);
            startActivity(intent);  // go to portal activity
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
//        try {
//            Log.v("Aidan " + TAG, "trying websocket connection");
//            aidanClient = new aidanWebSocket(wsurl + "/pins/socket", this);
//            aidanClient.connect();
//            //@Nick, todo: add your websocket try here too. Hopefully that works.
//            //You could also make a similar try/catch right below this one, might be a better idea
//        } catch (URISyntaxException e) {
//            Log.e(TAG, "Websocket Error: " + e.toString());
//            Log.e("Aidan WebSocket", "Websocket Error: " + e.toString());
//            e.printStackTrace();
//        }
    }

    /**
     * @author Aidan Foss
     * Loads map and fills it with all pin data.
     * Gathers and displays any relevant passed information from other activities
     * @param googleMap
     *
     * other functions are embedded here, which will be moved or depreciated in the future
     *      - progress fetching from progress activity will be changed to only be in this class.
     *      - login information will be moved to onOpen()
     *      - message information can be moved to onOpen()
     *      - individual pin information can just be recieved via WebSocket after creation.
     */
    @Override //This gets called when the map initializes. It only happens once, but its seperate from onCreate.
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;
        gMap.getUiSettings().setAllGesturesEnabled(false); //disables being able to move camera around
        this.gMap.moveCamera(CameraUpdateFactory.zoomTo(12));

        fillMap(); //fills the map with relevant information


        //Following statement and if/else check for user information or any passed pin information.
        //Its in onMap because of the pin information
        if (extras == null) {
            Log.i(TAG, "extras, missing any passed information");
            //make debug user, likely name aaa, password bbb, any relevant info
        } else {
            Log.i(TAG, "extras != null");
            if (extras.containsKey("message")) {
                textView.append(extras.getString("message")+ "\n");
            }
            if (extras.containsKey("username")){
                user = new User(
                        extras.getString("username"),
                        extras.getString("password"),
                        extras.getInt("userID"));
            }

            //create new pin with passed data //pinVector.add(new Pin(extras.getDouble("LATITUDE"),extras.getDouble("LONGITUDE"), (extras.getString("NAME") + "( " + extras.getDouble("LATITUDE") + ", " + extras.getDouble("LONGITUDE") + ")")));
//            Pin newPin = new Pin (extras.getDouble("LATITUDE"),extras.getDouble("LONGITUDE"),extras.getString("NAME"),extras.getInt("PINID"));
//            Marker newMarker = this.gMap.addMarker(new MarkerOptions().position(newPin.getPos()).title(newPin.getName()));
            if (extras.containsKey("discovered")) { //discover response
                textView.append("Pin with ID " + extras.getInt("pinId") + " discovered: " + extras.getBoolean("discovered")+ "\n");
                progressVolley.fetchProgress(extras.getInt("pinId"), new progressVolley.VolleyCallbackGet() {
                    @Override
                    public void onSuccess(int pinId, int userId, boolean discovered, int progressObjId) {
                        Log.d(TAG, "Progress Get Req: " + pinId + " " + userId + " " + discovered);
                        textView.append("Progress Data Received Via Volley Get Request: " + pinId + " " + userId + " " + discovered+ "\n");
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e(TAG, "fetchProgressData error: " + errorMessage);
                    }
                });
            }
            if (extras.containsKey("LATITUDE")) { //newpin response
                textView.append("New Pin Created with values: (" + extras.getString("NAME") + ", " + extras.getDouble("LATITUDE") + ", " + extras.getDouble("LONGITUDE") + ")\n");
            }
            if (extras.containsKey("PINID")) {
                int pinID = extras.getInt("PINID");
                if (WebSocketManager.getInstance().isConnected()) {
                    WebSocketManager.getInstance().aidanWS().send(String.valueOf(pinID));
                }
                //GET REQUEST

                pinVolley.fetchPinData(pinID, new pinVolley.FetchPinCallback() {
                    @Override
                    public void onSuccess(Pin pin) {
                        textView.append("Pin Data Received Via Volley Get Request: " + pin.getDebugDescription()+ "\n");
                        Log.d(TAG, "Pin Get Req: " + pin.getDebugDescription());
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        textView.append("Get request failed for pin ID " + pinID+ "\n");
                        Log.e(TAG, "fetchPinData error: " + errorMessage);
                    }
                });
                WebSocketManager.getInstance().sendAidan(String.valueOf(pinID));
            }
            if (extras.containsKey("LoginSuccess")) {
                textView.append("Login with value (" + extras.getBoolean("LoginSuccess") + ")\n");
            }
        }

        /*
         * @author Aidan Foss
         * Detects when a user clicks on a pin for more info
         * This is used for many reasons.
         * here are the steps:
         * 1) Grab the pin ID
         * 2) send a progress update to the server for logging
         * 3) display the pin as activated (change the icon)
         * 4) go to a more detailed screen for the clicked pin
         */
        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                //1 grab pin ID (creates temporary pin object)
                Pin clickPin = (Pin) Objects.requireNonNull(marker.getTag());

                //2 send progress update
                progressVolley.discoverPin(user.getID(), clickPin.getID(), new progressVolley.VolleyCallback() {
                    @Override
                    public void onSuccess(boolean discovered) {
                        ((Pin) Objects.requireNonNull(marker.getTag())).setTrue(); //sets discovery in the pin object inside the tag
                    }
                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e(TAG + " InfoWindowClick ProgressVolley", "Error Discovering Pin onCLick Handler: " + errorMessage);
                    }
                });

                //3 change the icon on the map
                marker.setIcon(BitmapDescriptorFactory.defaultMarker());

                //4 move to pinInfoScreen
                //create intent for more information screen
                Intent intent = new Intent(MapsActivity.this, PinInformationActivity.class);
                intent.putExtra("ID", clickPin.getID()); //pass clicked pins ID
                startActivity(intent);
            }
        });
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                //gMap.moveCamera(CameraUpdateFactory.newLatLng());
                //todo fix the weird camera movement when clicking on a pin on the map
                return false;
            }
        });

    }

    /**
     * @author Aidan Foss
     * fills map with markers, and creates relevant pin objects.
     * adds pin object to tags of marker for onclick listener.
     *      look at OnMarkerClick for more info
     *
     * Function that makes a volley request to recieve all pin data.
     * Uses url from MainActivity, and uses the googleMaps gMap declaration
     * from the top of the class.
     */
    private void fillMap() {
        String useURL = url + "/pins";
        Log.v(TAG, "fillMap() called");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, useURL, null,
            response -> {
                try {
                    //JSONObject jsonArray = response.getJSONObject("pins");
                    Log.d(TAG + "volley", "request success");

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonPin = response.getJSONObject(i);

                        int id = jsonPin.getInt("id");
                        double x = jsonPin.getDouble("x");
                        double y = jsonPin.getDouble("y");
                        String name = jsonPin.getString("name");
                        String snippet = "temporary snippet";
                        String description = "temporary description";
                        boolean discovered = response.getBoolean(i);

                        //progressVolley.fetchProgress();

                        Pin newPin = new Pin(x, y, name, snippet, description, id, discovered);
                        Log.d(TAG + "discover" + "volley", "pin created " + newPin.getDebugDescription());
                        if (newPin.getDiscovered()) {
                            Log.v(TAG + "discover", "discovered pin created: " + newPin.getDebugDescription());
                            Marker marker = this.gMap.addMarker(new MarkerOptions()
                                    .position(newPin.getPos())
                                    .title(newPin.getName())
                                    .snippet(newPin.getDescription())
                                    .icon(BitmapDescriptorFactory.defaultMarker())
                            );
                            assert marker != null;
                            marker.setTag(newPin);
                        }
                        else //create undiscovered pin
                        {
                            Log.v(TAG + "discover", "undiscovered pin created: " + newPin.getDebugDescription());
                            Marker marker = this.gMap.addMarker(new MarkerOptions()
                                    .position(newPin.getPos())
                                    .title(newPin.getName())
                                    .snippet(newPin.getDescription())
                                    .icon(smallUndiscoveredIcon)
                            );
                            assert marker != null;
                            marker.setIcon(smallUndiscoveredIcon);
                            Log.e(TAG + "discover", "test");
                            marker.setTag(newPin);
                        }
                    }
                    textView.append("CreatedPins\n");
                } catch (JSONException e) {
                    Log.e(TAG + "volley", "JSONException: " + e);
                    e.printStackTrace();
                }
            }, Throwable::printStackTrace);
        mQueue.add(request);
    }

    /**
     * @author Aidan Foss
     * Listener that resumes constants when reopening the map after
     * navigation. Resumes location data and websockets if they paused.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (!WebSocketManager.getInstance().isConnected()) {
            try {
                WebSocketManager.getInstance().openWebSocketConnection(user.getUsername(), this);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        startLocationUpdates();
    }

    /**
     * @author Aidan Foss
     * starts location updates if location permission is allowed
     */
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    /**
     * @author Aidan Foss
     * Listener that detects when activity is paused
     * calls another function that pauses location updates
     */
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    /**
     * @author Aidan Foss
     * stops location updates
     */
    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    /**
     * @author Aidan Foss
     * runs whenever a pin is recieved from the pins websocket
     * fetches relevant pin information and displays it live
     * @param wsPinIdInput from WebSocketListener
     */
    @Override
    public void onPinRecieved(int wsPinIdInput) {
        pinVolley.fetchPinData(wsPinIdInput, new pinVolley.FetchPinCallback() {
            @Override
            public void onSuccess(Pin pin) {
                textView.append("Pin Data Received Via WebSocket + Volley: " + pin.getDebugDescription() + "\n");
                Log.d("Aidan "+ TAG + " Volley Websocket", "Pin Get Req: " + pin.getDebugDescription());
            }

            @Override
            public void onFailure(String errorMessage) {
                textView.append("get request failed for pin ID " + wsPinIdInput + "\n");
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

        friendVolley.addfriendsReq(name, user, new friendVolley.addFriendCallback() {
            @Override
            public void onSuccess(User user) {
                textView.append("This user was added as a friend\n");
                Log.d("Nick "+ TAG + " Volley Websocket", "FriendAdd Get Req: ");
            }

            @Override
            public void onFailure(String errorMessage) {
                textView.append("This user was unable to be added as a friend\n");
                Log.e(TAG, "fetchuseraddData error: " + errorMessage);
            }
        });

    }
}