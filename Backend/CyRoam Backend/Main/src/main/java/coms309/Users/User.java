package coms309.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.Friends.FriendObj;
import coms309.Pin.Pin;
import coms309.Statistics.Statistics;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class User {
	//hello caleb todo remove this line :3
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uId;

    private String username;
    private String password;

    private int score;


    @OneToMany(fetch = FetchType.LAZY)
    private List<FriendObj> friends;

    @JsonIgnore
    @OneToOne
    private Statistics stats;

    @JoinColumn(name = "pins")
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Pin> pins;

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

    public Statistics getStatistics() {
        return stats;
    }

    public void setStatistics(Statistics stats) {
        this.stats = stats;
    }

    public List<Pin> getPins() {
        return pins;
    }
}
