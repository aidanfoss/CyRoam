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

    /**
     * @author Aidan Foss
     * returns the one and only copy of the whole manager
     * if it doesnt exist yet, creates it.
     * @return singleton instance of the manager
     */
    public static WebSocketManager getInstance() {
        if (instance == null) {
            instance = new WebSocketManager();
        }
        return instance;
    }

    /**
     * @author Aidan Foss
     * singleton function that opens all relevant websockets, allowing the rest of this
     * class to handle them.
     * @param username
     * @param listener
     * @throws URISyntaxException
     */

    public void openWebSocketConnection(String username, WebSocketListener listener) throws URISyntaxException {
        if (!isConnected) {
            aidanClient = new aidanWebSocket(wsurl + "/pins/socket", listener);
            aidanClient.connect();
            nickClient = new nickWebSocket(wsurl + "/friendSocket/" + username, listener);
            nickClient.connect();
            isConnected = true;
        }
    }

    /**
     * @author Aidan Foss
     * closes all relevant handled websockets at once.
     */
    public void closeWebSocketConnection() {
        if (isConnected && aidanClient.isOpen() && nickClient.isOpen()){
            aidanClient.close();
            nickClient.close();

            isConnected = false;
        }
    }

    /**
     * @author Aidan Foss
     * @return boolean if websocket is connected
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * @author Aidan Foss
     * @param text to send through pins websocket
     * likely to be renamed later to describe the websocket rather than author
     */
    public void sendAidan(String text){
        aidanClient.send(text);
    }
    /**
     * @author Aidan Foss
     * @return aidanClient Websocket for more specific use
     * likely to be renamed later to describe the websocket rather than author
     */
    public aidanWebSocket aidanWS(){
        return aidanClient;
    }
}
