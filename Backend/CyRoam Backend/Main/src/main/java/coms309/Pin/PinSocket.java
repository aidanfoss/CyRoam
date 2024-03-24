package coms309.Pin;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Controller
@ServerEndpoint(value = "/pins/socket")
public class PinSocket {

    private static PinRepository pinRepository;

    @Autowired
    public void setPinRepository(PinRepository repo) {pinRepository = repo;}

    private List<Session> sessions = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(PinSocket.class);

    @OnOpen
    public void onOpen(Session session) {
        logger.info("onOpen Started");
        sessions.add(session);

    }

    @OnMessage
    public void onMessage(Session session, String pin) {
        for (int i = 0; i < sessions.size(); i++) {
            try {
                sessions.get(i).getBasicRemote().sendText(pin);
            } catch (IOException e) {
                logger.info("IOException: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }
}
