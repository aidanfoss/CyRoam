package com.lg1_1.cyroam;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LeaderBoard extends AppCompatActivity {
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
    private Button backButton2;

    private TextView outputTextbox;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_leader_board);
        ConstraintLayout constraintLayout = findViewById(R.id.main3);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        backButton2 = findViewById(R.id.Leaderboardout);
        outputTextbox = findViewById(R.id.LeaderBoardInsertText);
        worldChampionReq();
        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaderBoard.this, PortalScreenActivity.class);
                startActivity(intent);
            }
        });

    }

    private void worldChampionReq(){
        String url = mainURL + "/leaderBoard";

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
                        outputTextbox.setText("World Champions:\n");
                        for (int i = 0; i < 5; i++){
                            JSONObject friendobj = response.getJSONObject(i);
                            //JSONObject friend = jsonArray.getJSONObject(i);


                            String curUser = friendobj.getString("user");
                            int score = friendobj.getInt("score");
                            String output = curUser + " " + score;

                            outputTextbox.append(curUser + ": " + score + "\n");
                            Log.i(TAG, output);


                        }

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
}