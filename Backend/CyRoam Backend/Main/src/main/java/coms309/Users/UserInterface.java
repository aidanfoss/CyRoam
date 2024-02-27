package coms309.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Zach Merwin
 *
 */

public interface UserInterface extends JpaRepository<User, Long> {

    User findByuId(int uId);

    void deleteById(int uId);

    //User findByLaptop_Id(int uId);

    User findByUsername(String username);
}

