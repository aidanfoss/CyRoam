package com.lg1_1.cyroam.websockets;

import static com.lg1_1.cyroam.MainActivity.wsurl;

import android.content.Context;

import org.java_websocket.WebSocket;

import java.net.URISyntaxException;

public class WebSocketManager {
    private static WebSocketManager instance;
    private WebSocket webSocket;
    private boolean isConnected = false;

    public aidanWebSocket aidanClient;
    public nickWebSocket nickClient;

    private WebSocketManager(){

    }

    public static WebSocketManager getInstance() {
        if (instance == null) {
            instance = new WebSocketManager();
        }
        return instance;
    }

    public void openWebSocketConnection(String username, WebSocketListener listener) throws URISyntaxException {
        if (!isConnected) {
            aidanClient = new aidanWebSocket(wsurl + "/pins/socket", listener);
            aidanClient.connect();
            nickClient = new nickWebSocket(wsurl + "/friendSocket/" + username, listener);
        }
    }

    public void closeWebSocketConnection() {
        if (isConnected && webSocket != null){
            aidanClient.close();
            nickClient.close();

            isConnected = false;
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void sendAidan(String text){
        aidanClient.send(text);
        return;
    }

    public aidanWebSocket aidanWS(){
        return aidanClient;
    }
}
