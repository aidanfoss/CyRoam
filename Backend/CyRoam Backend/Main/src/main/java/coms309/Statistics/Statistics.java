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
    private int userId;
    private int numDiscoveredPins;


    @JoinColumn(name = "pins")
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Pin> pins;

    public Statistics(int userId) {
        this.userId = userId;
        this.numDiscoveredPins = 0;
        pins = new ArrayList<>();
    }

    public Statistics() {

    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }


}
