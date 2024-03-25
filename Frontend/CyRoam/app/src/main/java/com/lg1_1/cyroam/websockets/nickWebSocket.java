package com.lg1_1.cyroam.websockets;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class nickWebSocket extends WebSocketClient {

    private final String TAG = "Nick Websocket";
    private final WebSocketListener webSocketListener;
    //do alt enter to fix the bug above. It'll autofill for you each required function
    //do a lot of Log.v(TAG, "info")'s
    //look at my onMessage class, implement the onFriendRequestReceived interface
    //i have the interface at the bottom of mapsActivity for you.

    public nickWebSocket(String serverUri2, WebSocketListener listener) throws URISyntaxException {
        super(new URI(serverUri2));
        this.webSocketListener = listener;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.v(TAG, "connected");
    }

    @Override
    public void onMessage(String name) {
        Log.v(TAG, "message received: " + name);
        //hopefully the message will be a JSON
        try {
            //convert the message into a json object
            JSONObject jsonObject = new JSONObject(name);
            //use json object to get data on the pins ID
            String invite = jsonObject.getString("name");
            //return pins ID through listener
            webSocketListener.onfredReqRecieved(invite);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.v(TAG, "connection closed");
    }

    @Override
    public void onError(Exception ex) {
        Log.w(TAG, "NickWebSocket error: " + ex.toString());
    }



}
