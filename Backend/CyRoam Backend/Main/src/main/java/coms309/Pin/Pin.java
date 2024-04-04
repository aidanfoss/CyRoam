package coms309.Pin;

import coms309.Comment.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonTypeId;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Pin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments;
    private double x;
    private double y;
    private String name;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public Pin(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        comments = new ArrayList<>();
    }

    public Pin() {
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addComment(Comment comment) {
         comments.add(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }
}
