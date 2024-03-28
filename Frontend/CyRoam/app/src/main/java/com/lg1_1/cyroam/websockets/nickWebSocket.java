package com.lg1_1.cyroam.websockets;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
/**
 * nicholas's websocket class
 * @author Nicholas Kirschbaum
 */
public class nickWebSocket extends WebSocketClient {
    /**
     * @author Nicholas Kirschbaum
     * tag for debugging
     */
    private final String TAG = "Nick Websocket";
    /**
     * @author Nicholas Kirschbaum
     * websocket listener
     */
    private final WebSocketListener webSocketListener;
    //do alt enter to fix the bug above. It'll autofill for you each required function
    //do a lot of Log.v(TAG, "info")'s
    //look at my onMessage class, implement the onFriendRequestReceived interface
    //i have the interface at the bottom of mapsActivity for you.
    /**
     * @author Nicholas Kirschbaum
     * nickweb socket class sets things up
     */
    public nickWebSocket(String serverUri2, WebSocketListener listener) throws URISyntaxException {
        super(new URI(serverUri2));
        this.webSocketListener = listener;
    }
    /**
     * @author Nicholas Kirschbaum
     * tells the user he is connected
     */
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.v(TAG, "connected");
    }
    /**
     * @author Nicholas Kirschbaum
     * on message sends json object request
     */

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
    /**
     * @author Nicholas Kirschbaum
     * tells user has closed websocket
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.v(TAG, "connection closed");
    }
    /**
     * @author Nicholas Kirschbaum
     * tells user there is an error websocket
     */
    @Override
    public void onError(Exception ex) {
        Log.w(TAG, "NickWebSocket error: " + ex.toString());
    }



}
