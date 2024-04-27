package coms309.Fog;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double x;
    private double y;

    private String imagePath;

    public Fog(double x, double y, String imagePath) {
        this.x = x;
        this.y = y;
        this.imagePath = imagePath;
    }

    public Fog() {

    }

    void setImagePath(String path) {
        imagePath = path;
    }
}
