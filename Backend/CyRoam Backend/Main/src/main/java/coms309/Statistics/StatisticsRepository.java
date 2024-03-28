package coms309.Statistics;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Statistics findById(int id);

    @Transactional
    void deleteById(int id);
}
