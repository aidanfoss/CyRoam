package coms309.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    Comment findById(int id);

    @Transactional
    void deleteById(int id);
}
