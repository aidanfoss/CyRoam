package com.lg1_1.cyroam.websockets;

import android.util.Log;

import org.java_websocket.WebSocketListener;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;

public class commentsWebSocket extends WebSocketClient {
    private final String TAG = "Comment Websocket";
    private WebSocketListener webSocketListener;

    public commentsWebSocket(String serverUri, WebSocketListener listener) throws URISyntaxException{
        super(new URI(serverUri));
        this.webSocketListener = listener;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.v(TAG,"Comments connected");
        //send passed pinID
    }

    @Override
    public void onMessage(String message) {
        Log.v(TAG, "comment message received: " + message);

        try {
            //parse message as a single comment;
            webSocketListener.onPreparePing(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.v(TAG, "connection closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        Log.w(TAG, "aidanWebSocket error: " + ex.toString());
    }
}

//id of the comment i want to comment on
