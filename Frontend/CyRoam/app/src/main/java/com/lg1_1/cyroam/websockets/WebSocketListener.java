package com.lg1_1.cyroam.websockets;

/**
 * Listener that is implemented in the maps function
 * allows easy websocket handling
 */
public interface WebSocketListener {
    void onPinRecieved(int id);


    void onfredReqRecieved(String name);
}
