package coms309;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonTypeId;

@Entity
public class Pin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

}
