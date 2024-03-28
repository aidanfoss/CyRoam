package com.lg1_1.cyroam.websockets;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Handles the Pin Websocket.
 * @author Aidan Foss
 * recieves pin ID's which are immediately used in MapsActivity to display newly created pins live
 */
public class aidanWebSocket extends WebSocketClient{
    private final String TAG = "Aidan Websocket";
    private WebSocketListener webSocketListener;

    /**
     * @author Aidan Foss
     * constructor for pins websocket
     * @param serverUri server URL that is used for connection
     * @param listener import listener (typically will be this)
     * @throws URISyntaxException to prevent errors
     */
    public aidanWebSocket(String serverUri, WebSocketListener listener) throws URISyntaxException{
        super(new URI(serverUri));
        this.webSocketListener = listener;
    }

    /**
     * @author Aidan Foss
     * logs connection in logcat, can handle other things in the future
     * @param handshakedata The handshake of the websocket instance
     */
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.v(TAG, "connected");
    }

    /**
     * @author Aidan Foss
     * activates WebSocketListener to be used in MapsActivity on message recieved
     * @param message The UTF-8 decoded message that was received.
     */
    @Override
    public void onMessage(String message) {
        Log.v(TAG, "message received: " + message);
        //hopefully the message will be a JSON
        try {
            //use string.valueof to get data on the pins ID
            int pinID = Integer.parseInt(message);
            //return pins ID through listener
            webSocketListener.onPinRecieved(pinID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Aidan Foss
     * Logs a closed connection, along with reason
     * @param code
     *            The codes can be looked up here: {@link CloseFrame}
     * @param reason
     *            Additional information string
     * @param remote
     *            Returns whether or not the closing of the connection was initiated by the remote host.
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.v(TAG, "connection closed: " + reason);
    }

    /**
     * @author Aidan Foss
     * Logs any websocket related errors
     * @param ex The exception causing this error
     */
    @Override
    public void onError(Exception ex) {
        Log.w(TAG, "aidanWebSocket error: " + ex.toString());
    }
}

