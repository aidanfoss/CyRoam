package coms309.Statistics;

import coms309.Pin.Pin;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.Users.User;
import jakarta.persistence.*;

@Entity
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uId")
    @JsonIgnore
    private User user;
    private int numDiscoveredPins;

    private int rank;

    private int fogDiscovered;

    private int score;



    public Statistics(int numDiscoveredPins) {
        this.numDiscoveredPins = numDiscoveredPins;
    }

    public Statistics() {

    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User userc) {
        this.user=userc;
        //return user;
    }

    public void setNumDiscoveredPins(int numDiscoveredPins) {
        this.numDiscoveredPins = numDiscoveredPins;
    }
    public int getNumDiscoveredPins() {
        return numDiscoveredPins;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getFogDiscovered() {
        return fogDiscovered;
    }

    public void setFogDiscovered(int fogDiscovered) {
        this.fogDiscovered = fogDiscovered;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
