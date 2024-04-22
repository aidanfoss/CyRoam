package coms309.Discovery;

import coms309.Pin.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscoveryRepository extends JpaRepository<Discovery, Long> {


    @Query("SELECT d.pin FROM Discovery d WHERE d.user.uId = :user_id")
    List<Pin> findPinsByUser(@Param("user_id") long user_id);
}
