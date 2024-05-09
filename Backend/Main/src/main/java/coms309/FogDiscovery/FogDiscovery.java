package coms309.FogDiscovery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.Fog.Fog;
import coms309.Users.User;
import jakarta.persistence.*;

@Entity
public class FogDiscovery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fog_id")
    @JsonIgnore
    private Fog fog;

    public FogDiscovery(User u, Fog f) {
        user = u;
        fog = f;
    }

    public FogDiscovery() {

    }

    User getUser() {
        return user;
    }

    Fog getFog() {
        return fog;
    }
}
