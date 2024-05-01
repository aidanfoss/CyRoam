package com.lg1_1.cyroam;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lg1_1.cyroam.Managers.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    private String TAG = "SettingsActivity";
    /**
     * Takes url from Main
     * @author Nicholas Kirschbaum
     */
    private String mainURL = MainActivity.url;

    //private Button backbuttontoportal;

    private Button removeButton;
    private Button darkmodeButton;
    private Button leaderboardPermissionButton;

    private TextView OutputText;

    private EditText removefriend;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        ConstraintLayout constraintLayout = findViewById(R.id.settingScreenId);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        //backbuttontoportal = findViewById(R.id.settingsbackbutton);
        removeButton =  findViewById(R.id.removeFriendButton);
        darkmodeButton = findViewById(R.id.darkModeButton);
        removefriend = findViewById(R.id.textViewRemoveFriend);
        leaderboardPermissionButton = findViewById(R.id.buttonRemoveSelfFromLeaderBoard);
        OutputText = findViewById(R.id.settingScreenOutputText);
        /*
        backbuttontoportal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsActivity.this, PortalScreenActivity.class);
                startActivity(intent);  // go to SignupActivity
            }
        }); */
        darkmodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().setStyle(R.raw.style_json2);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* This removes the fridn that is in the EditText */
                String curUsername = LoginManager.getInstance().getUser().getUsername();
                String noLongerfriend = removefriend.getText().toString();
                removefriend(curUsername, noLongerfriend);

            }
        });
        /**
         * The user can choose whether they appear on the leaderboard
         */
        leaderboardPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = LoginManager.getInstance().getUser().getUsername();;
               leaderBoardReq(username, new LoginActivity.VolleyCallback() {
                    @Override
                    public void onSuccess(boolean isTrue) {
                        if(isTrue == true) {
                            OutputText.setText("You now dont show on the leader board");
                        }
                        else{
                            OutputText.setText("You now show on the leader board");
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e(TAG, "make string req onFailure " + errorMessage);
                    }

                });

            }
        });

    }



    /**
     * This removes friends from users friend list
     * @author Nicholas Kirschbaum
     */
    private void removefriend(String curUsername,String Newfriend){
        String url = mainURL + "/deleteFriend/" + curUsername + "/" + Newfriend;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                response -> {
                    try{
                        JSONArray jsonArray = response.getJSONArray("deleteFriend");
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
                return headers;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        // Adding request to request queue
        queue.add(request);
    }

    /**
     * This req removes or add the user to the leader board if they meet the requirements
     * @author Nicholas Kirschbaum
     */
    private void leaderBoardReq(String curUsername, final LoginActivity.VolleyCallback callback){
        //private void makeStringReq(String curUsername, String password){
        String url = mainURL + "/userCheck/" + curUsername;


        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                //userInfo2,
                // so the issue is that its saying that there is an error
                //Log.v(TAG, "respo success: "),
                response -> {
                    Log.v(TAG, "respo success: " + response.toString());
                    try{
                        Log.v(TAG, "request success");
                        boolean isTrue = response.getBoolean("isUser");
                        int i = 0;
                        //inputuser = new User(curUsername, password, i);


                        //return isTrue;
                        callback.onSuccess(isTrue);

                    }catch (JSONException e){
                        Log.e(TAG, "catch error: " + e.getMessage());
                        e.printStackTrace();
                        callback.onFailure(e.getMessage());
                    }

                    // output = response.toString();

                },
                error -> {
                    Log.e(TAG, "error lambda error: " + error.getMessage());
                    // tvResponse.setText(error.getMessage());
                }

        ){
//
        };

        // Adding request to request queue
        queue.add(request);
//        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
    /**
     *Volley callback for Log in
     */
    public interface VolleyCallback{
        void onSuccess(boolean isTrue);
        void onFailure(String errorMessage);
    }
}