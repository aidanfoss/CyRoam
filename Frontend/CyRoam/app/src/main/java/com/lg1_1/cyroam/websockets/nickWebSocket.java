package com.lg1_1.cyroam.websockets;

import android.content.Context;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;

public class nickWebSocket extends WebSocketClient {
    private final String TAG = "Nick Websocket";
    private WebSocketListener webSocketListener;

    public nickWebSocket(String serverUri, WebSocketListener listener) throws URISyntaxException {
        super(new URI(serverUri));
        this.webSocketListener = listener;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
    //do alt enter to fix the bug above. It'll autofill for you each required function
    //do a lot of Log.v(TAG, "info")'s
    //look at my onMessage class, implement the onFriendRequestRecieved interface
    //i have the interface at the bottom of mapsActivity for you.
}
