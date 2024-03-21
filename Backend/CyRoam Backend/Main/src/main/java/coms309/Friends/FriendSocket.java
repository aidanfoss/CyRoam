package coms309.Friends;

import com.mysql.cj.protocol.Message;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Controller;
import jakarta.websocket.OnMessage;
import java.io.IOException;

@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/friendSocket/{username}")  // this is Websocket url
public class FriendSocket {
    @OnOpen
    public void onOpen(Session session) throws IOException {
        // Get session and WebSocket connection
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
    // Handle new messages
    }

    @OnClose
    public void onClose(Session session) throws IOException {
// WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
// Do error handling here
    }

}
