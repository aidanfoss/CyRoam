package coms309.Pin;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.EOFException;
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

    private static List<Session> sessions;

    private final Logger logger = LoggerFactory.getLogger(PinSocket.class);

    @OnOpen
    public void onOpen(Session session) throws IOException {
        if (sessions == null) {
            sessions = new ArrayList<>();
            pinRepository.save(new Pin(4, 4, "test"));
        }
        logger.info("onOpen Started");
        logger.info("Session " + session.getId() + " connected");
        sessions.add(session);
        logger.info("Number of sessions: " + sessions.size());
    }

    @OnMessage
    public void onMessage(Session session, String pin) {
        logger.info("onMessage Started with session " + session.getId());
        for (int i = 0; i < sessions.size(); i++) {
            try {
                logger.info("Sent to session " + sessions.get(i).getId());
                sessions.get(i).getBasicRemote().sendText(pin);
            } catch (EOFException e) {
                logger.error("EOFException: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                logger.error("IOException: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    @OnClose
    public void onClose(Session session) {
            logger.info("Session " + session.getId() + " disconnected");
            sessions.remove(session);
            logger.info("Number of sessions: " + sessions.size());
    }
}
