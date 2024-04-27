package coms309.Fog;

import coms309.FogDiscovery.FogDiscovery;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Fog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<FogDiscovery> fogDiscoveries;

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
