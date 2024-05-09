package coms309.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.Pin.*;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    private String text;

    private long time;

    @ManyToOne
    @JoinColumn(name = "pin")
    @JsonIgnore
    private Pin pin;

    public Comment(String text, Pin pin) {
        time = System.currentTimeMillis();
        this.text = text;
        this.pin = pin;
        pin.getComments().add(this);
    }

    public Comment() {

    }

    public Pin getPin() {
        return pin;
    }

    public String getText() {
        return text;
    }
}
