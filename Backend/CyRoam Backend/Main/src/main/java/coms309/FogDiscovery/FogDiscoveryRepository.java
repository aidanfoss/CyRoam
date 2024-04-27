package coms309.FogDiscovery;

import coms309.Discovery.Discovery;
import coms309.Fog.Fog;
import coms309.Pin.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FogDiscoveryRepository extends JpaRepository<FogDiscovery, Long> {
    @Query("SELECT d.fog FROM FogDiscovery d WHERE d.user.uId = :user_id")
    List<Fog> findFogByUser(@Param("user_id") long user_id);
}
