package com.lg1_1.cyroam.websockets;

public interface WebSocketListener {
    void onPinRecieved(int id);


    void onfredReqRecieved(String name);
}
