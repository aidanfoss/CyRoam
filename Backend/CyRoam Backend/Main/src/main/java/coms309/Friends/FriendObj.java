package coms309.Friends;

import coms309.Users.User;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.security.SecurityProperties;


//hh
@Entity
public class FriendObj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int entry;


   //how do I stop this from displaying password/ is there a way to just connect the UID element/username element?
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "uId")
//    private User user;

    //needs testing
  //  @ManyToOne(cascade = CascadeType.ALL)
   // @JoinColumn(name = "username")
    private String curUsername;

    //private int friend_uId;

    private String friendUsername;

    //private int friendState;
    public FriendObj(String curUsername, String friendUsername) {
        this.curUsername = curUsername;
        this.friendUsername = friendUsername;

    }
    public FriendObj()
    {

    }


    //setters and getters

    public void setCurUsername(String username) {
        this.curUsername = username;
    }
    public String getCurUsername() {
        return curUsername;
    }
    public void setFriendUsername(String username) {
        this.friendUsername = username;
    }
    public String getFriendUsername() {
        return friendUsername;
    }
}
