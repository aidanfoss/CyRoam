package coms309.Comment;
import coms309.Pin.PinRepository;
import jakarta.transaction.Transactional;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@ServerEndpoint(value = "/pins/{id}/comments")
public class CommentsSocket {
    private static CommentsRepository commentsRepository;
    private static PinRepository pinRepository;


    @Autowired
    public void setCommentsRepository(CommentsRepository repo) {commentsRepository = repo;}

    @Autowired
    public void setPinRepository(PinRepository repo) {pinRepository = repo;}

    private static List<Session> sessions;
    private static List<Integer> ids;

    private final Logger logger = LoggerFactory.getLogger(CommentsSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("id") int id) throws IOException {
        if (sessions == null) {
            //index these together so each session has a pin id if you call the same index
            sessions = new ArrayList<>();
            ids = new ArrayList<>();
        }
        logger.info("onOpen Started");
        logger.info("Session " + session.getId() + " connected");
        //look through comments for any that have been posted on given pin
        for (int i = 0; i < commentsRepository.count(); i++) {
                //send comments
            if (commentsRepository.findAll().get(i).getPin().getId() == id) {
                session.getBasicRemote().sendText(commentsRepository.findAll().get(i).getText());
            }
        }
        sessions.add(session);
        ids.add(id);
        logger.info("Number of sessions: " + sessions.size());
    }

    @OnMessage
    public void onMessage(Session session, String text) {
        logger.info("onMessage Started with session " + session.getId());
        int index = sessions.indexOf(session);
        int pinId = ids.get(index);
        logger.info("pinId: " + pinId);
        commentsRepository.save(new Comment(text, pinRepository.findById(pinId)));
        for (int i = 0; i < sessions.size(); i++) {
            try {
                logger.info("Sent to session " + sessions.get(i).getId());
                //Ensure we are only sending to sessions with the same pin id
                if (ids.get(i) == pinId && i != index) {
                    sessions.get(i).getBasicRemote().sendText(text);
                }
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
        logger.info("Session " + session.getId() + " has closed the websocket");
        int index = sessions.indexOf(session);
        sessions.remove(index);
        ids.remove(index);
        logger.info("Number of sessions remaining: " + sessions.size());
    }
}
