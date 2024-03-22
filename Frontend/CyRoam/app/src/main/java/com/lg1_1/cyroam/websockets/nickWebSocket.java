package com.lg1_1.cyroam.websockets;

import org.java_websocket.client.WebSocketClient;

public class nickWebSocket extends WebSocketClient {
    private final String TAG = "Nick Websocket";
    private WebSocketListener webSocketListener;
    //do alt enter to fix the bug above. It'll autofill for you each required function
    //do a lot of Log.v(TAG, "info")'s
    //look at my onMessage class, implement the onFriendRequestRecieved interface
    //i have the interface at the bottom of mapsActivity for you.
}
