package coms309.Fog;

import coms309.FogDiscovery.FogDiscovery;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Fog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FogDiscovery> fogDiscoveries;

    private double x;
    private double y;

    public Fog(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Fog() {

    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}