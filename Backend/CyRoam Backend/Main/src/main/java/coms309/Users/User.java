package coms309.Users;

import coms309.Friends.FriendObj;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uId;
    private String username;
    private String password;


   // @OneToMany
    //private List<FriendObj> friends;


    //contructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
      //  friends = new ArrayList<>();

    }
    public User()
    {
        //friends = new ArrayList<>();
    }
    //getters and setters
    public int getuId(){
        return uId;
    }

    public void setId(int uId){
        this.uId = uId;
    }

    public String getUsername(){
        return username;
    }

    public void setName(String username){
        this.username = username;
    }
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }


}
