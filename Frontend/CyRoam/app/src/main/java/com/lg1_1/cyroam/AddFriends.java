package com.lg1_1.cyroam;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lg1_1.cyroam.Managers.LoginManager;
import com.lg1_1.cyroam.Managers.WebSocketManager;
import com.lg1_1.cyroam.NicksAdapters.FriendsAddListAdapter;
import com.lg1_1.cyroam.objects.Friend;
import com.lg1_1.cyroam.websockets.WebSocketListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This screen holds invites from other people
 * as well as allows you to send invites
 * Add Friends is a screen that is only accessable via friends activity
 */
public class AddFriends extends AppCompatActivity implements WebSocketListener {


    /**
     * @author Nicholas Kirschbaum
     * tag for debugging
     */
    private String TAG = "ADDFriendActivity";
    /**
     * @author Nicholas Kirschbaum
     * Takes url from Main
     */
    private String mainURL = MainActivity.url;
    /**
     * @author Nicholas Kirschbaum
     * Is a button that sends invite to the user that is typed
     */
    private Button friendSearch;

    private Button bybyButton;
    private EditText usernameEditText;

    Boolean checker = false;
    private RequestQueue queue;

    ArrayList<Friend> list2 = new ArrayList<>();
    /**
     * @author Nicholas Kirschbaum
     * prints out screen with button on it(more in the works)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_friends);
        /**
         * //Gradient Background
        ConstraintLayout constraintLayout = findViewById(R.id.main2);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        */



        friendSearch = findViewById(R.id.searchbutton);
        bybyButton = findViewById(R.id.addBackButton);
        usernameEditText = findViewById(R.id.FriendSearch);

        ListView mViewList = (ListView) findViewById(R.id.listView2);
        //ArrayList<Friend> list = new ArrayList<>();

        String curUsername = LoginManager.getInstance().getUser().getUsername();
        findfriendsReq(curUsername);

        try {
            WebSocketManager.getInstance().openWebSocketConnection(curUsername, this);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
       // Friend one = new Friend("John", 32, 23);
       // Friend two = new Friend("steve", 32, 23);
       //list.add(one);
        //list.add(two);

        //get list of friends here
       // FriendsAddListAdapter friendsListAdapter = new FriendsAddListAdapter(this, R.layout.format2_listview, list);
       // mViewList.setAdapter((ListAdapter) friendsListAdapter);




        friendSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Newfriend = usernameEditText.getText().toString();
                addfriendsReq(curUsername,Newfriend);
                //createnotif();
                try {
                    // send message
                    WebSocketManager.getInstance().nickClient.send(Newfriend);
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            }


        });
        bybyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFriends.this, FriendsActivity.class);
                startActivity(intent);
            }


        });


    }
    private void addfriendsReq(String curUsername,String Newfriend){
        String url = mainURL + "/addFriend";

        // Convert input to JSONObject
        JSONObject userInfo = new JSONObject();
        try{

            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            userInfo.put("curUsername", curUsername);
            userInfo.put("friendUsername", Newfriend);
            userInfo.put("friendStatus", 0);


        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                userInfo,
                response -> {
                    try{

                        checker = true;
                        JSONArray jsonArray = response.getJSONArray("addfriend");
                        Log.i(TAG, "request success");


                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    // output = response.toString();

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,error.getMessage());
                        // tvResponse.setText(error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                //                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //                params.put("param1", "value1");
                //                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        queue.add(request);
    }

    /**
     * @author Nicholas Kirschbaum
     * When called it created a notification
     */
    public void createnotif(String name) {
        Log.v("Nick Websocket", "message made to createnotif " + name);
        String id = "Whatch this work";
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel(id);
            if (channel == null) {
                channel = new NotificationChannel(id, "Channel line", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("[Channel Discription]");
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 1000, 200, 340});
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(channel);
            }


        }
        Intent notifIntent = new Intent(this, NotificationActivity.class);
        notifIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, notifIntent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, notifIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.drawable.iowa_state_clipart_4_removebg_preview)
                .setContentTitle("Recieved!!!")
                .setContentText("Your Recieved a friend request")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{100, 1000, 200, 340})
                .setAutoCancel(false)
                .setTicker("Notification");
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat m = NotificationManagerCompat.from(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
           // public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        m.notify(1, builder.build());

    }


    private void findfriendsReq(String curUsername){
        String url = mainURL + "/friendRequests/" + curUsername;

        //JsonObjectRequest request = new JsonObjectRequest(
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                //userInfo,
                response -> {
                    try{
                        //JSONArray jsonArray = response.getJSONArray(6);
                        list2.clear();
                        // List<JSONObject> list = response.getJSONArray("friends");
                        Log.i(TAG, "request success");
                        //outputtext.setText(curUsername + " Friends:\n");
                        //Friend nice = new Friend("ryan", 0, 10000);
                        //list2.add(nice);
                        for (int i = 0; i < response.length(); i++){
                            JSONObject friendobj = response.getJSONObject(i);
                            //JSONObject friend = jsonArray.getJSONObject(i);


                            String friendUser = friendobj.getString("curUsername");
                            String curUser = friendobj.getString("friendUsername");
                            //output = curUser + " " + friendUser;
                            Friend free = new Friend(friendUser, 0, 10000+i);
                            list2.add(free);
                            //outputtext.append(friendUser + "\n");
                            Log.i(TAG, curUser);


                        }
                        initializeListAddAdapter();

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    // output = response.toString();

                },
                error -> {
                    Log.e(TAG,error.getMessage());
                    // tvResponse.setText(error.getMessage());
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                //                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //                params.put("param1", "value1");
                //                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        queue.add(request);
    }

    private void initializeListAddAdapter() {
        FriendsAddListAdapter friendsListAdapter = new FriendsAddListAdapter(this, R.layout.format2_listview, list2);
        ListView mViewList = findViewById(R.id.listView2);
        mViewList.setAdapter((ListAdapter) friendsListAdapter);
    }


    @Override
    public void onPinRecieved(int id) {

    }

    @Override
    public void onfredReqRecieved(String name) {
        createnotif(name);
    }
}