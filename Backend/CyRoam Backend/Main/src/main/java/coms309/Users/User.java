package coms309.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.Discovery.Discovery;
import coms309.Friends.FriendObj;
import coms309.Pin.Pin;
import coms309.Statistics.Statistics;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class User {
	//hello caleb todo remove this line :3
    //@OneToMany
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uId;

    @Column(unique = true)
    private String username;
    private String password;

    private int score;
    private int permissions;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = CascadeType.ALL)
    private List<FriendObj> friends;

    @JsonIgnore
    @OneToOne
    private Statistics stats;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Discovery> discoveries;

    //contructor
    public User(String username, String password, int score, int permissions) {
        this.username = username;
        this.password = password;
        this.score = score;
        this.permissions = permissions;
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
    public void setScore(int score){
        this.score = score;
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



    public Statistics getStatistics() {
        return stats;
    }

    public void setStatistics(Statistics stats) {
        this.stats = stats;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public int getPermissions() {
        return permissions;
    }
}
