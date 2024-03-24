package com.lg1_1.cyroam.websockets;

import java.net.URI;

public abstract class WebSocketClient {
    public WebSocketClient(URI uri) {
    }

    public abstract void onOpen(ServerHandshake handshakedata);
}
