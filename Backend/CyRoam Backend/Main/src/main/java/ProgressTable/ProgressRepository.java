package ProgressTable;

import coms309.Pin;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Progress findById(int id);

    @Transactional
    void deleteById(int id);
}
