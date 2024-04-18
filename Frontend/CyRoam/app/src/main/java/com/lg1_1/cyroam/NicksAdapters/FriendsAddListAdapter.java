package com.lg1_1.cyroam.NicksAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lg1_1.cyroam.MainActivity;
import com.lg1_1.cyroam.R;
import com.lg1_1.cyroam.objects.Friend;
import com.lg1_1.cyroam.objects.User;
import com.lg1_1.cyroam.websockets.WebSocketListener;
import com.lg1_1.cyroam.Managers.WebSocketManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class FriendsAddListAdapter extends ArrayAdapter<Friend> implements WebSocketListener {
    /**
     * @author Nicholas Kirschbaum
     * tag for debugging
     */
    private String TAG = "FriendActivity";
    /**
     * @author Nicholas Kirschbaum
     * Takes url from Main
     */
    private String mainURL = MainActivity.url;
    private Context context1;

    private User use;

    private RequestQueue queue;
    int stuff;
    public FriendsAddListAdapter(Context context, int formatListview, ArrayList<Friend> list) {
        super(context, formatListview, list);
        context1 = context;
        stuff = formatListview;
    }

    public View getView(int position, View convert, ViewGroup parent){

        queue = Volley.newRequestQueue(this.getContext());


        String name = getItem(position).getName();
        int theirId = getItem(position).getTheirId();
        int theirScore = getItem(position).getTheirScore();

        Friend friend = new Friend(name, theirScore, theirId);

        LayoutInflater layoutInflater = LayoutInflater.from(context1);
        convert = layoutInflater.inflate(stuff, parent,false);

        TextView user = (TextView) convert.findViewById(R.id.textView17);
        Button btn = convert.findViewById(R.id.buttonProblem);
        btn.setFocusable(false);
        btn.setClickable(false);
        //String str1 = Integer.toString(score);
        //String str2 = Integer.toString(Id);
        user.setText("Friend: "+name);
        String CurUser = "bossf";
        try {
            WebSocketManager.getInstance().openWebSocketConnection(CurUser, this);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic goes here



                //String CurUser = use.getUsername();

                addfriends(CurUser,name);

                putfriends(CurUser,name);
                //addfriends(name,CurUser);
                try {
                    // send message
                    WebSocketManager.getInstance().nickClient.send(name);
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }

                //user.setText(":3");
            }
        });



        return convert;

    }

    private void addfriends(String curUsername,String Newfriend){
        String url = mainURL + "/addFriend";

        // Convert input to JSONObject
        JSONObject userInfo = new JSONObject();
        try{

            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            userInfo.put("curUsername", curUsername);
            userInfo.put("friendUsername", Newfriend);



        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                userInfo,
                response -> {
                    try{


                        JSONArray jsonArray = response.getJSONArray("addfriend");
                        Log.i(TAG, "request success");
                        //outputtext.setText(curUsername + " Friends:\n");
                        // for (int i = 0; i < jsonArray.length(); i++){
                        //  JSONObject friend = jsonArray.getJSONObject(i);

                        //  String curUser = friend.getString("curUsername");
                        // String friendUser = friend.getString("friendUsername");
                        // output = curUser + " " + friendUser;

                        //outputtext.append(friendUser + "\n");
                        //Log.i(TAG, output);


                        //}

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
    private void putfriends(String curUsername,String Newfriend){
        String url = mainURL + "/updateStatus";

        // Convert input to JSONObject
        JSONObject userInfo = new JSONObject();
        try{

            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            userInfo.put("curUsername", Newfriend);
            userInfo.put("friendUsername", curUsername);
            userInfo.put("friendStatus", true);


        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                userInfo,
                response -> {
                    try{


                        JSONArray jsonArray = response.getJSONArray("addfriend");
                        Log.i(TAG, "request success");
                        //outputtext.setText(curUsername + " Friends:\n");
                        // for (int i = 0; i < jsonArray.length(); i++){
                        //  JSONObject friend = jsonArray.getJSONObject(i);

                        //  String curUser = friend.getString("curUsername");
                        // String friendUser = friend.getString("friendUsername");
                        // output = curUser + " " + friendUser;

                        //outputtext.append(friendUser + "\n");
                        //Log.i(TAG, output);


                        //}

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

    @Override
    public void onPinRecieved(int id) {

    }

    @Override
    public void onfredReqRecieved(String name) {

    }
}
