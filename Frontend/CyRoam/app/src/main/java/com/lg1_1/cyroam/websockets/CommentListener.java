package com.lg1_1.cyroam.websockets;

/**
 * Listener that implements WebSocket Handling for Comments
 * @author Aidan Foss
 */
public interface CommentListener {
    void onCommentReceived(String comment);
}
