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

    private int time;

    @ManyToOne
    @JoinColumn(name = "pin")
    @JsonIgnore
    private Pin pin;

    public Comment(int time, String text) {
        this.time = time;
        this.text = text;
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
