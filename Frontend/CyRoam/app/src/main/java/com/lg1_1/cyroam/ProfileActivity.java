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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.lg1_1.cyroam.objects.Friend;

import org.json.JSONException;
import org.json.JSONObject;

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
        rankOutput = findViewById(R.id.rankTextiew);
        distanceOutput = findViewById(R.id.distanceTextView);
        //scoreOutput = findViewById(R.id.textViewScoreOutput);
        //pinOutput = findViewById(R.id.textViewPinsCount);


    }

    private void findfriendsReq(String curUsername){
        String url = mainURL + "/friends/" + curUsername;

        //JsonObjectRequest request = new JsonObjectRequest(
        JsonArrayRequest request = new JsonArrayRequest(
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
                        for (int i = 0; i < response.length(); i++){
                            JSONObject friendObj = response.getJSONObject(i);

                            JSONObject friendUserObj = friendObj.getJSONObject("friendUser");
                            int userId = friendUserObj.getInt("uId");
                            String friendUsername = friendUserObj.getString("username");
                            int score = friendUserObj.getInt("score");
                            //int score = Integer.parseInt(friendobj.getString("score"));
                            //output = curUser + " " + friendUser;
                            Friend free = new Friend(friendUsername, score, userId);

                            //outputtext.append(friendUser + "\n");
                            Log.i(TAG, friendUsername);


                        }


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