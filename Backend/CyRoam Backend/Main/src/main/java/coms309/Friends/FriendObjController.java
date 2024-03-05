package coms309.Friends;

import coms309.Users.User;
import coms309.Friends.FriendObjInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendObjController {
//jj

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
    FriendObj addFriend(@RequestBody FriendObj friendObj){
        String friendUsername = friendObj.getFriendUsername();
        if (friendUsername == null)
            return null;
        friendObjInterface.save(friendObj);
        return friendObj;

    }
    @DeleteMapping(path = "/deleteFriend")
    FriendObj deleteFriend(@RequestBody FriendObj passedFriendObj){
        String passedfriendUsername = passedFriendObj.getFriendUsername();
        String passedcurUsername = passedFriendObj.getCurUsername();


        if (passedfriendUsername == null ||passedcurUsername== null) {
            return null;
        }
        if(friendObjInterface.findByCurUsername(passedfriendUsername)==null){
            return null;
        }
        friendObjInterface.delete(passedFriendObj);
        return passedFriendObj;

    }
}
