package coms309.Users;

import coms309.Friends.FriendObj;
import coms309.Statistics.Statistics;
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

    private int score;


    @OneToMany(fetch = FetchType.LAZY)
    private List<FriendObj> friends;

    @OneToOne
    private Statistics stats;

    //contructor
    public User(String username, String password, int score) {
        this.username = username;
        this.password = password;
        this.score = score;
        friends = new ArrayList<>();

    }
    public User()
    {
        friends = new ArrayList<>();
    }
    //getters and setters
    public int getuId(){
        return uId;
    }

    public void setId(int uId){
        this.uId = uId;
    }

    public int getScore(){
        return score;
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

    public List<FriendObj> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendObj> friends) {
        this.friends = friends;
    }

    public void addPhones(FriendObj phone){
        this.friends.add(phone);
    }


}
