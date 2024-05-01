package coms309.Statistics;

import coms309.Users.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Statistics findById(int id);

    Statistics findByUser(User user);

    @Transactional
    void deleteById(int id);
}
