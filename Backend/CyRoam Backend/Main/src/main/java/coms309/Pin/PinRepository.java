package coms309.Pin;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinRepository extends JpaRepository<Pin, Long> {
    Pin findById(int id);

    @Transactional
    void deleteById(int id);
}
