package com.lg1_1.cyroam.websockets;

import static com.lg1_1.cyroam.MainActivity.wsurl;

import java.net.URISyntaxException;

public class WebSocketManager {
    private static WebSocketManager instance;
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
            nickClient.connect();
            isConnected = true;
        }
    }

    public void closeWebSocketConnection() {
        if (isConnected && aidanClient.isOpen() && nickClient.isOpen()){
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
    }

    public aidanWebSocket aidanWS(){
        return aidanClient;
    }
}
