package coms309.Statistics;

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
    private int pinId;
    private boolean discovered;

    public Statistics(int userId, int pinId, boolean discovered) {
        this.userId = userId;
        this.pinId = pinId;
        this.discovered = discovered;
    }

    public Statistics() {

    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setPinId(int pinId) {
        this.pinId = pinId;
    }

    public int getPinId() {
        return pinId;
    }
}
