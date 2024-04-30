package coms309.Discovery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.Pin.Pin;
import coms309.Users.User;
import jakarta.persistence.*;

@Entity
public class Discovery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pin_id")
    @JsonIgnore
    private Pin pin;

    public Discovery(User u, Pin p) {
        pin = p;
        user = u;
    }

    public Discovery() {

    }

    //Getters should be the only thing necessary

    public Pin getPin() {
        return pin;
    }

    public User getUser() {
        return user;
    }

    public void setPin(Pin pin) {
        this.pin = pin;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
