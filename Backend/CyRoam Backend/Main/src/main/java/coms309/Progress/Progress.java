package coms309.Progress;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int pinId;
    private boolean discovered;

    public Progress() {
        discovered = false;
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
