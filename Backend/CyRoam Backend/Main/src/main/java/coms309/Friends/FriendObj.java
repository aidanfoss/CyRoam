package coms309.Friends;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
//@JoinColumn(name = "uId")
//    private User use

    //needs testing
   @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
   @JoinColumn(name = "userId")
   @JsonIgnore
   private User user;

   private String curUsername;


    //private int friend_uId;

    private String friendUsername;

    private Boolean friendStatus;

    //private int friendState;
    public FriendObj(String curUsername, String friendUsername, Boolean accept) {
        this.curUsername = curUsername;
        this.friendUsername = friendUsername;
        this.friendStatus = accept;

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

    public void setfriendStatus(Boolean friendStatus) {
        this.friendStatus = friendStatus;
    }
    public Boolean getfriendStatus() {
        return friendStatus;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
