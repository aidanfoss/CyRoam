package coms309.Fog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FogRepository extends JpaRepository<Fog, Long> {
    Fog findById(int id);
}
