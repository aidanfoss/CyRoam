package coms309.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.Pin.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    public Comment(long time, String text, Pin pin) {
        this.time = time;
        this.text = text;
        this.pin = pin;
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
