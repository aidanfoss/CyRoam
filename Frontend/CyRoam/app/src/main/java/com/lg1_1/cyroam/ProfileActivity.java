package com.lg1_1.cyroam;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lg1_1.cyroam.Managers.LoginManager;

import org.json.JSONException;

/**
 * This shows the Users basic information and basic achievements
 * @author Nicholas Kirschbaum
 */
public class ProfileActivity extends AppCompatActivity {
    /**
     * tag for debugging
     */
    private String TAG = "PROFILEACIVITY";
    /**
     *
     * Takes url from Main
     */
    private String mainURL = MainActivity.url;
    private TextView rankOutput;

    private TextView distanceOutput;

    private TextView scoreOutput;

    private TextView pinOutput;

    private TextView fogOutput;

    private TextView friendOutput;

    private TextView titlemain;

    private Button backButton;

    private RequestQueue queue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ConstraintLayout constraintLayout = findViewById(R.id.mainlayoutforProfile);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


        /**
         * Set up credientals
         */
        String curUsername;
        int id = 0;
        String idstring;
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            curUsername = LoginManager.getInstance().getUser().getUsername();
            id = LoginManager.getInstance().getUser().getID();
            idstring = Integer.toString(id);

        }
        else{
            curUsername = getIntent().getStringExtra("friendName");
            idstring = getIntent().getStringExtra("friendId");
            id = Integer.parseInt(idstring);
            //findfriendsReq(usernamestring);
        }

        titlemain = findViewById(R.id.profileTitleTextiew);

        rankOutput = findViewById(R.id.rankTextiew);
        scoreOutput = findViewById(R.id.scoreTextView);
        distanceOutput = findViewById(R.id.distanceTextView);
        fogOutput = findViewById(R.id.forExpelledTextView);
        friendOutput = findViewById(R.id.friendCollectionTextView23);
        pinOutput = findViewById(R.id.pinTallyTextView23);
        profileBuildReq(id);
        titlemain.setText(curUsername + "'s\n" + "Profile" + "\n" + "ID: " + idstring + "\n");

    }

    private void profileBuildReq(int id){
        String url = mainURL + "/Statistics/" + id;

        //JsonObjectRequest request = new JsonObjectRequest(
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                //userInfo,
                response -> {
                    try{
                        //JSONArray jsonArray = response.getJSONArray(6);

                        // List<JSONObject> list = response.getJSONArray("friends");
                        Log.i(TAG, "request success");
                        //outputtext.setText(curUsername + " Friends:\n");



                        //JSONObject friendUserObj = friendObj.getJSONObject("statistics");
                        //int userId = friendUserObj.getInt("uId");
                        //String friendUsername = friendUserObj.getString("username");
                            int score = response.getInt("score");
                            scoreOutput.setText("Score: " + score);
                            int numPins = response.getInt("numDiscoveredPins");
                            pinOutput.setText("Pins Discovered: " + numPins + "/" +"100");
                            int rank = response.getInt("rank");
                            rankOutput.setText("Rank: " + rank);
                            int forDiscore = response.getInt("fogDiscovered");
                            fogOutput.setText("Fog Cleared: " + forDiscore + "%");

                            //int score = Integer.parseInt(friendobj.getString("score"));

                            /*
                            {
                                    "uId": 1,
                                    "username": "bossf",
                                    "password": "123",
                                    "score": 50,
                                    "permissions": 2,
                                    "statistics": {
                                        "id": 7,
                                        "numDiscoveredPins": 8,
                                        "rank": 1,
                                        "fogDiscovered": 50,
                                        "score": 50
                                    }
                                }
                             */




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

        };

        // Adding request to request queue
        queue.add(request);
    }
}