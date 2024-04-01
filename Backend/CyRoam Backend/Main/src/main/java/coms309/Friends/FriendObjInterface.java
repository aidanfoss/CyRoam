package coms309.Friends;

import coms309.Friends.FriendObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
//ll
public interface FriendObjInterface extends JpaRepository<FriendObj, Long> {
    FriendObj findByEntry(int entry);

    void deleteByEntry(int entry);


    //may or may not be useful
    FriendObj findByCurUsernameOne(String curUsername);

    @Query
    List<FriendObj> findByCurUsername(String curUsername);



}
