package coms309.Friends;

import coms309.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendObjInterface extends JpaRepository<FriendObj, Long> {
    FriendObj findByEntry(int entry);

    void deleteByEntry(int entry);


    //may or may not be useful
    FriendObj findByUsername(String username);



}
