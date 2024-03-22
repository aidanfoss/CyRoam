package com.lg1_1.cyroam.websockets;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class aidanWebSocket extends WebSocketClient{
    private final String TAG = "Aidan Websocket";
    private WebSocketListener webSocketListener;
    public aidanWebSocket(String serverUri, WebSocketListener listener) throws URISyntaxException{
        super(new URI(serverUri));
        this.webSocketListener = listener;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.v(TAG, "connected");
    }

    @Override
    public void onMessage(String message) {
        Log.v(TAG, "message recieved: " + message);
        //hopefully the message will be a JSON
        try {
            //convert the message into a json object
            JSONObject jsonObject = new JSONObject(message);
            //use json object to get data on the pins ID
            int pinID = jsonObject.getInt("id");
            //return pins ID through listener
            webSocketListener.onPinRecieved(pinID);
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
        Log.w(TAG, "aidanWebSocket error: " + ex.toString());
    }
}

