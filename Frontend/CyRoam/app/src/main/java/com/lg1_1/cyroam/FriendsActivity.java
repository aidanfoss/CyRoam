package com.lg1_1.cyroam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.lg1_1.cyroam.util.Friend;
import com.lg1_1.cyroam.util.FriendsListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * has been implemented yet
 * @author Nicholas Kirschbaum
 */
public class FriendsActivity extends AppCompatActivity {


    /**
     *  tag for debugging
     * @author Nicholas Kirschbaum
     * tag for debugging
     */
    private String TAG = "ADDFriendActivity";
    /**
     * Takes url from Main
     * @author Nicholas Kirschbaum

     */
    private String mainURL = MainActivity.url;

    private Button backButton2;
    private Button toAddFriends;

    private RequestQueue queue;
    ArrayList<Friend> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        backButton2 = findViewById(R.id.homeButton);
        toAddFriends= findViewById(R.id.addButton);

        ListView mViewList = (ListView) findViewById(R.id.listvire);

        //ArrayList<Friend> list = new ArrayList<>();
        Friend one = new Friend("John", 32, 23);
        Friend two = new Friend("steve", 32, 23);
        list.add(one);
        list.add(two);
        //String curUsername ="bossf";
        //findfriends(curUsername);

        //get list of friends here
        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(this, R.layout.format_listview, list);
        mViewList.setAdapter((ListAdapter) friendsListAdapter);



        //backButton2.findViewById(R.id.);
        //toAddFriends.findViewById(R.id.AddButton);
        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsActivity.this, PortalScreenActivity.class);
                startActivity(intent);
            }
        });

        toAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsActivity.this, AddFriends.class);
                startActivity(intent);
            }
        });


    }

    private void findfriends(String curUsername){
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
                            JSONObject friendobj = response.getJSONObject(i);
                            //JSONObject friend = jsonArray.getJSONObject(i);


                            String curUser = friendobj.getString("curUsername");
                            String friendUser = friendobj.getString("friendUsername");
                            //output = curUser + " " + friendUser;
                            Friend free = new Friend(friendUser, 75, i);
                            list.add(free);
                            //outputtext.append(friendUser + "\n");
                            //Log.i(TAG, output);


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