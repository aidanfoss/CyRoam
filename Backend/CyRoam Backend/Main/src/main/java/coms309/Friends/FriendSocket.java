package coms309.Friends;

import com.mysql.cj.protocol.Message;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.websocket.OnMessage;
import java.io.IOException;
import java.util.*;

@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/friendSocket/{username}")  // this is Websocket url
public class FriendSocket {
    private static FriendObjInterface friendRepo;
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();
    @Autowired
    public void setFriendRepository(FriendObjInterface repo) {
        friendRepo = repo;
    }
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        // Get session and WebSocket connection
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        Set<String> notifiedFriends = new HashSet<>(); // Keep track of notified friends for this session

        // Check if anyone has added you as a friend
        while (true) {
            List<FriendObj> f = friendRepo.findByCurUsername(username);

            for (FriendObj friendObj : f) {
                String friendUsername = friendObj.getFriendUsername();

                // Check if this friend has already been notified
                if (!notifiedFriends.contains(friendUsername)) {
                    // Notify user about new friend
                    friendAdded(friendUsername, username);
                    notifiedFriends.add(friendUsername); // Add friend to notified list
                }
            }

            try {
                Thread.sleep(5000); // Adjust sleep duration as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        // nick could send me friedn object user wants to accept
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
    private void friendAdded(String friendUsername,String curUsername) {
        try {

            usernameSessionMap.get(curUsername).getBasicRemote().sendText(friendUsername + " added you as a friend!");

        }
        catch (IOException e) {
            //logger.info("Exception: " + e.getMessage().toString());
            //e.printStackTrace();

        }
    }

}
