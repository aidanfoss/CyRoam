package com.lg1_1.cyroam.websockets;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;

public class aidanWebSocket extends WebSocketClient{
    private final String TAG = "Aidan Websocket";
    public aidanWebSocket(String serverUri) throws URISyntaxException{
        super(new URI(serverUri));
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.v(TAG, "connected");
    }

    @Override
    public void onMessage(String message) {
        Log.v(TAG, "message recieved");
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
