package coms309.Friends;

import coms309.Users.User;
import coms309.Friends.FriendObjInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendObjController {


    @Autowired
    public FriendObjInterface friendObjInterface;
    private String success = "{\"message\":\"success ( :\"}";
    private String failure = "{\"message\":\"failure ) :\"}";
    @GetMapping(path = "/friends")
    List<FriendObj> requestFriends(@RequestBody FriendObj Justusername){
        String username = Justusername.getCurUsername();
        List<FriendObj> f =friendObjInterface.findByCurUsername(username);
       return f;
    }
    @PostMapping(path = "/addFriend")
     String addFriend(@RequestBody FriendObj friendObj){
        String friendUsername = friendObj.getFriendUsername();
        if (friendUsername == null)
            return failure;
        friendObjInterface.save(friendObj);
        return success;

    }
    @DeleteMapping(path = "/deleteFriend")
    String deleteFriend(@RequestBody FriendObj passedFriendObj){
        String passedfriendUsername = passedFriendObj.getFriendUsername();
        String passedcurUsername = passedFriendObj.getCurUsername();


        if (passedfriendUsername == null ||passedcurUsername== null) {
            return failure;
        }
        if(friendObjInterface.findByCurUsername(passedfriendUsername)==null){
            return failure;
        }
        friendObjInterface.delete(passedFriendObj);
        return success;

    }
}
