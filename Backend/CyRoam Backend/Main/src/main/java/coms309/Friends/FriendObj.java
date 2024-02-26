package coms309.Friends;

import coms309.Users.User;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

public class FriendObj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int entry;


    //how do I stop this from displaying password/ is there a way to just connect the UID element/username element?
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uId")
    private User user;

    private String username;

    private int friend_uId;

    private String friend_username;

    private int friendState;
}
