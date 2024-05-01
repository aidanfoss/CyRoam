package com.lg1_1.cyroam;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;
import static com.lg1_1.cyroam.MainActivity.url;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lg1_1.cyroam.Managers.LoginManager;
import com.lg1_1.cyroam.Managers.WebSocketManager;
import com.lg1_1.cyroam.aidansActivities.PinInformationActivity;
import com.lg1_1.cyroam.objects.Pin;
import com.lg1_1.cyroam.objects.User;
import com.lg1_1.cyroam.volley.friendVolley;
import com.lg1_1.cyroam.volley.pinVolley;
import com.lg1_1.cyroam.volley.progressVolley;
import com.lg1_1.cyroam.websockets.WebSocketListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Vector;

/**
 * Hub Activity; displays map, pins, info, friend requests and stores most info
 * @author Aidan Foss
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, WebSocketListener {
    private final String TAG = "MapsActivityTag"; //debugging tag
    private final LatLng centralCampus = new LatLng(42.02703247809317, -93.6464125793965);
    Vector<Marker> fogVector;
    private Bundle extras;
    private int currentSetting;
    private User user;
    protected TextView textView;
    private BitmapDescriptor smallUndiscoveredIcon;
    private BitmapDescriptor bitmapUserIcon;
    private BitmapDescriptor smallDiscoveredIcon;
    private BitmapDescriptor smallFogIcon;

    //define volley classes and requestQueue
    private friendVolley friendVolley;
    private progressVolley progressVolley;
    protected RequestQueue mQueue; // define volley request queue

    private final LatLng swBounds = new LatLng(42.02221344519929, -93.65174685767133);
    private final LatLng neBounds = new LatLng(42.03296498762718, -93.64322212670656);

    //TODO define user object here to determine what they can and cant do
    //this can also change what does and doest display (ex:no fog on admin account, no distance limit etc)

    //location & locationPermissions things
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    protected static FusedLocationProviderClient fusedLocationClient;
    protected LocationRequest locationRequest;
    private LocationCallback locationCallback;
    protected double lat = 0;
    protected double lng = 0;


    GoogleMap gMap;
    Marker userMarker;
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
        progressVolley = com.lg1_1.cyroam.volley.progressVolley.getInstance(this);
        fogVector = new Vector<>();
        //gather extras
        extras = getIntent().getExtras();
        currentSetting = LoginManager.getInstance().getStyle();
        //if the user isnt logged on, then kick them back to the login screen.
        LoginManager.getInstance().setUser();
        if (LoginManager.getInstance().getUser() == null) {
            Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        //initialize websocketConnections
        try {
            Log.v(TAG, "onCreate Websocket Try");
            user = LoginManager.getInstance().setUser();
            WebSocketManager.getInstance().openWebSocketConnection(user.getUsername(), this);
        } catch (URISyntaxException e) {
            Log.w(TAG, "onCreate WebSocket Fail");
            throw new RuntimeException(e);
        }


        //define icons as BitmapDescriptors for the .icon call in Marker Declarations
        Bitmap smallUndiscovered = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.undiscovered), 96, 96, false);
        smallUndiscoveredIcon = BitmapDescriptorFactory.fromBitmap(smallUndiscovered);
        Bitmap userIcon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.circle), 64, 64, false);
        bitmapUserIcon = BitmapDescriptorFactory.fromBitmap(userIcon);
        Bitmap smallDiscovered = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.discovered), 96, 96, false);
        smallDiscoveredIcon = BitmapDescriptorFactory.fromBitmap(smallDiscovered);
        Bitmap fogIcon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fog), 640, 640, false);
        smallFogIcon = BitmapDescriptorFactory.fromBitmap(fogIcon);

        //defines volley queue for fillMap todo remove this and put it in pinvolley
        mQueue = Volley.newRequestQueue(this);

        //define variables used for grabbing user location information
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, 1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(500)
                .setMaxUpdateDelayMillis(1000)
                .build();

        //check for permissions and ask for them
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        /*
           set visibility of buttons and UI based on users permission value.
           If the user has no permissions, only initialize
           buttons related to score and friends
        */
        //define all UI elements
        map = findViewById(R.id.map); //defines the map in the UI
        textView = findViewById(R.id.textView2); //defines debug text screen

        FloatingActionButton portalButton = findViewById(R.id.portalButton);
        FloatingActionButton portalButton1 = findViewById(R.id.portalButton1); //Friends
        FloatingActionButton portalButton2 = findViewById(R.id.portalButton2); //new Pin
        FloatingActionButton portalButton3 = findViewById(R.id.portalButton3); //progress
        FloatingActionButton portalButton4 = findViewById(R.id.portalButton4); //leaderboard
        FloatingActionButton portalButton5 = findViewById(R.id.portalButton5); //settings
        FloatingActionButton portalButton6 = findViewById(R.id.portalButton6);

        //check users permission value and change function of anything relevant based on that
        switch (LoginManager.getInstance().getPermission()){
            case 0: //basic user
                portalButton.setOnClickListener(v-> {
                    if(portalButton1.getVisibility() == View.INVISIBLE) {
                        portalButton1.setVisibility(View.VISIBLE);
                        portalButton3.setVisibility(View.VISIBLE);
                        //portalButton4.setVisibility(View.VISIBLE);
                        portalButton5.setVisibility(View.VISIBLE);
                        portalButton6.setVisibility(View.VISIBLE);
                    } else {
                        portalButton1.setVisibility(View.INVISIBLE);
                        portalButton3.setVisibility(View.INVISIBLE);
                        //portalButton4.setVisibility(View.INVISIBLE);
                        portalButton5.setVisibility(View.INVISIBLE);
                        portalButton6.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case 1: //pin creator
                portalButton.setOnClickListener(v-> {
                    if(portalButton1.getVisibility() == View.INVISIBLE) {
                        portalButton1.setVisibility(View.VISIBLE);
                        portalButton3.setVisibility(View.VISIBLE);
                        portalButton2.setVisibility(View.VISIBLE);
                        //portalButton4.setVisibility(View.VISIBLE);
                        portalButton5.setVisibility(View.VISIBLE);
                        portalButton6.setVisibility(View.VISIBLE);
                    } else {
                        portalButton1.setVisibility(View.INVISIBLE);
                        portalButton3.setVisibility(View.INVISIBLE);
                        portalButton2.setVisibility(View.INVISIBLE);
                        //portalButton4.setVisibility(View.INVISIBLE);
                        portalButton5.setVisibility(View.INVISIBLE);
                        portalButton6.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case 2: //admin
                portalButton.setOnClickListener(v-> {
                    if(portalButton1.getVisibility() == View.INVISIBLE) {
                        portalButton1.setVisibility(View.VISIBLE);
                        portalButton3.setVisibility(View.VISIBLE);
                        portalButton2.setVisibility(View.VISIBLE);
                        portalButton4.setVisibility(View.VISIBLE);
                        portalButton5.setVisibility(View.VISIBLE);
                        portalButton6.setVisibility(View.VISIBLE);
                    } else {
                        portalButton1.setVisibility(View.INVISIBLE);
                        portalButton3.setVisibility(View.INVISIBLE);
                        portalButton2.setVisibility(View.INVISIBLE);
                        portalButton4.setVisibility(View.INVISIBLE);
                        portalButton5.setVisibility(View.INVISIBLE);
                        portalButton6.setVisibility(View.INVISIBLE);
                    }
                });
                textView.setVisibility(View.VISIBLE);
                break;
        }



        //implement buttons
        portalButton1.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
        portalButton2.setOnClickListener(v -> { //newPinButton
            Intent intent = new Intent(MapsActivity.this, NewPinActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            startActivity(intent);  // go to NewPinActivity
        });
        portalButton3.setOnClickListener(v -> { //progress Activity
            Intent intent = new Intent(MapsActivity.this, LeaderBoard.class);
            startActivity(intent);
        });
        portalButton4.setOnClickListener(v-> {
            Intent intent = new Intent(MapsActivity.this, AdminUserControl.class);
            startActivity(intent);
        });
        portalButton5.setOnClickListener(v-> { //settings (dark mode, light mode, etc)
            Intent intent = new Intent(MapsActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
        portalButton6.setOnClickListener(v-> { //settings (dark mode, light mode, etc)
            Intent intent = new Intent(MapsActivity.this, FriendsActivity.class);
            startActivity(intent);
        });


        //map fragment info
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        // Initialize location callback to receive location updates
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    if (userMarker!= null){
                        userMarker.setPosition(latLng);
                    }


                    if(currentSetting != LoginManager.getInstance().getStyle()){
                        try {
                            // Customise the styling of the base map using a JSON object defined
                            // in a raw resource file.
                            boolean success = gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapsActivity.this, LoginManager.getInstance().getStyle()));
                            if (!success) {
                                Log.e(TAG, "Style parsing failed.");
                            }
                        } catch (Resources.NotFoundException e) {
                            Log.e(TAG, "Can't find style. Error: ", e);
                        }
                    }

                    for(int i=0; i < fogVector.size();i++){
                        Pin inPin = (Pin) fogVector.get(i).getTag();
                        if (inPin != null && inPin.isFog()) {
                            double tolerance = 0.0025;
                            LatLng markerPos = fogVector.get(i).getPosition();
                            if (Math.abs(markerPos.latitude - latLng.latitude) <= tolerance) {
                                if (Math.abs(markerPos.longitude - latLng.longitude) <= tolerance+0.0015) {
                                    com.lg1_1.cyroam.volley.progressVolley.getInstance(MapsActivity.this).clearFog(inPin.getID(), new progressVolley.ClearFogCallback() {
                                        @Override
                                        public void onSuccess(boolean discovered) {
                                            //Log.v(TAG, "success!");
                                        }
                                        @Override
                                        public void onFailure(String errorMessage) {

                                        }
                                    });
                                    //marker.setPosition(new LatLng(0,0));
                                    fogVector.get(i).setVisible(false);
                                    fogVector.get(i).remove();
                                }
                            }
                        }
                    }
                }
            }
        };
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
     *      - individual pin information can just be received via WebSocket after creation.
     */
    @Override //This gets called when the map initializes. It only happens once, but its seperate from onCreate.
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;
        googleMap.setLatLngBoundsForCameraTarget(new LatLngBounds(swBounds,neBounds));
        googleMap.setMinZoomPreference(15f);
        googleMap.setMaxZoomPreference(16f);
        gMap.getUiSettings().setAllGesturesEnabled(true); //disables being able to move camera around
        this.gMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(centralCampus));

        fillMap(); //fills the map with undiscovered pins
        fillMapDiscovered(); //fills the map with pins that have already been discovered
        fillMapFog(); //fills with fog

        //create the users icon
        userMarker = this.gMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).icon(bitmapUserIcon).zIndex(5.0f).anchor(0.5f,0.5f));
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, LoginManager.getInstance().getStyle()));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
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

            //create new pin with passed data //pinVector.add(new Pin(extras.getDouble("LATITUDE"),extras.getDouble("LONGITUDE"), (extras.getString("NAME") + "( " + extras.getDouble("LATITUDE") + ", " + extras.getDouble("LONGITUDE") + ")")));
            if (extras.containsKey("discovered")) { //discover response
                textView.append("Pin with ID " + extras.getInt("pinId") + " discovered: " + extras.getBoolean("discovered")+ "\n");
                progressVolley.fetchProgress(new progressVolley.VolleyCallbackGet() {
                    @Override
                    public void onSuccess(int pinId, int userId, boolean discovered) {
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

                 pinVolley.getInstance(this).fetchPinData(pinID, new pinVolley.FetchPinCallback() {
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
        gMap.setOnInfoWindowClickListener(marker -> {
            //1 grab pin ID (creates temporary pin object)
            Pin clickPin = (Pin) marker.getTag();
            //2 check if user is close enough and pin clicked is not fog
            if (clickPin != null && !clickPin.isFog()) {
                double tolerance = 0.0025;
                if (Math.abs(marker.getPosition().latitude - lat) <= tolerance) {
                    if (Math.abs(marker.getPosition().longitude - lng) <= tolerance+0.0015) {
                        //3 send progress update
                        progressVolley.discoverPin(clickPin.getID(), new progressVolley.VolleyCallback() {
                            @Override
                            public void onSuccess() {
                                ((Pin) Objects.requireNonNull(marker.getTag())).setTrue(); //sets discovery in the pin object inside the tag

                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                Log.e(TAG + " InfoWindowClick ProgressVolley", "Error Discovering Pin onCLick Handler: " + errorMessage);
                            }
                        });
                        //3 change the icon on the map
                        marker.setIcon(smallDiscoveredIcon);

                        //4 move to pinInfoScreen
                        //create intent for more information screen
                        Intent intent = new Intent(MapsActivity.this, PinInformationActivity.class);
                        intent.putExtra("ID", clickPin.getID()); //pass clicked pins ID
                        startActivity(intent);
                    }
                }
            }
        });
        gMap.setOnMapClickListener(latLng -> gMap.moveCamera(CameraUpdateFactory.zoomTo(15)));
    }

    /**
     * @author Aidan Foss
     * Fills the map with pins that have not been discovered by the user yet
     */
    private void fillMap() {
        Log.v(TAG, "fillMap() called");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + "/users/" + LoginManager.getInstance().getUser().getID() + "/hasNotDiscovered", null,
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
                        String splash = jsonPin.getString("splash");
                        String description = jsonPin.getString("description");
                        String imagePath = jsonPin.getString("imagePath");

                        Pin newPin = new Pin(x, y, name, splash, description, imagePath, id, false);
                        Log.v(TAG + "discover", "undiscovered pin created: " + newPin.getDebugDescription());
                        Marker marker = this.gMap.addMarker(new MarkerOptions()
                                .position(newPin.getPos())
                                .title(newPin.getName())
                                .snippet(newPin.getSplash())
                                .icon(smallUndiscoveredIcon)
                                .zIndex(2.0f)
                                .anchor(0.5f,0.5f)
                        );
                        assert marker != null;
                        marker.setIcon(smallUndiscoveredIcon);
                        marker.setTag(newPin);
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
     * Fills the map with pins that have been discovered
     */
    private void fillMapDiscovered() {
        Log.v(TAG, "fillMapDiscovered() called");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url+"/users/" + LoginManager.getInstance().getUser().getID() + "/pins", null,
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
                        if (name.contains("test") || name.contains("Test")){
                            continue;
                        }
                        String splash = jsonPin.getString("splash");
                        String description = jsonPin.getString("description");
                        String imagePath = jsonPin.getString("imagePath");

                        Pin newPin = new Pin(x, y, name, splash, description, imagePath, id, true);
                        Log.v(TAG + "discover", "discovered pin created: " + newPin.getDebugDescription());
                        Marker marker = this.gMap.addMarker(new MarkerOptions()
                                .position(newPin.getPos())
                                .title(newPin.getName())
                                .snippet(newPin.getDescription())
                                .icon(smallDiscoveredIcon)
                                .zIndex(1.0f)
                                .anchor(0.5f,0.5f)
                        );
                        assert marker != null;
                        marker.setTag(newPin);

                    }
                    textView.append("CreatedDiscoveredPins\n");
                } catch (JSONException e) {
                    Log.e(TAG + "volley", "JSONException: " + e);
                    e.printStackTrace();
                }
            }, Throwable::printStackTrace);
        mQueue.add(request);
    }

    /**
     * @author Aidan Foss
     * Fills the map with fog that has not been cleared
     * I do not need to call fog that has been cleared, as theres no reason to ever display it.
     */
    private void fillMapFog() {
        Log.v(TAG, "fillMapFog() called");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + "/users/" + LoginManager.getInstance().getUser().getID() + "/hasNotCleared", null,
                response -> {
                    try {

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonPin = response.getJSONObject(i);

                            int id = jsonPin.getInt("id");
                            double x = jsonPin.getDouble("x");
                            double y = jsonPin.getDouble("y");
                            //todo fog progress
                            Pin fogPin = new Pin(x,y,id,true);
                            //Log.d(TAG + "discover" + "volley", "fog created");
                            Marker fog = gMap.addMarker(new MarkerOptions().icon(smallFogIcon).position(new LatLng(x,y)).anchor(0.5f,0.5f));

                            if (fog != null) {
                                fogVector.add(fog);
                                fog.setTag(fogPin);
                            }
                        }
                        textView.append("CreatedFog\n");
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
        if (locationCallback != null && fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Location updates stopped successfully."))
                    .addOnFailureListener(e -> Log.e(TAG, "Failed to stop location updates.", e));
        }
    }

    /**
     * @author Aidan Foss
     * runs whenever a pin is recieved from the pins websocket
     * fetches relevant pin information and displays it live
     * @param wsPinIdInput from WebSocketListener
     */
    @Override
    public void onPinRecieved(int wsPinIdInput) {
        pinVolley.getInstance(this).fetchPinData(wsPinIdInput, new pinVolley.FetchPinCallback() {
            @Override
            public void onSuccess(Pin pin) {
                textView = findViewById(R.id.textView2);  // Ensure this ID matches your layout
                if (textView != null) {
                    textView.append("Pin Data Received Via WebSocket + Volley: " + pin.getDebugDescription() + "\nSuccess!\n");
                    Log.d("Aidan "+ TAG + " Volley Websocket", "Pin Get Req: " + pin.getDebugDescription());
                } else {
                    Log.e("MapsActivity", "TextView not initialized");
                }
                Marker marker = gMap.addMarker(new MarkerOptions()
                        .position(pin.getPos())
                        .title(pin.getName())
                        .snippet(pin.getDescription())
                        .icon(BitmapDescriptorFactory.defaultMarker())
                        .anchor(0.5f,0.5f)
                );
                assert marker != null;
                marker.setTag(pin);
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