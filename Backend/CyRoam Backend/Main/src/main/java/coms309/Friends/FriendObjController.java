package coms309.Friends;

import coms309.Statistics.Statistics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
public class FriendObjController {
//jj

    @Autowired
    public FriendObjInterface friendObjInterface;
    private String success = "{\"message\":\"success ( :\"}";
    private String failure = "{\"message\":\"failure ) :\"}";
    //change to contain info in url
    @Operation(summary = "Gets a list of all friend objects that have the entered username as the current username")
    @ApiResponse(responseCode = "200", description = "Successfully returned all friend objects", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = FriendObj.class)) })
    @GetMapping(path = "/friends/{username}")

    List<FriendObj> requestFriends(@PathVariable String username){
        ///String username = Justusername.getCurUsername();
        //List<FriendObj>  trueFriends = null;

        List<FriendObj>  friends =friendObjInterface.findByCurUsername(username);
        friends.removeIf(friend -> !friend.getfriendStatus());

        return friends;
    }


    @Operation(summary = "creates new friend object")
    @ApiResponse(responseCode = "200", description = "Successfully Created Object", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = FriendObj.class)) })
    @PostMapping(path = "/addFriend")

    FriendObj addFriend(@RequestBody FriendObj friendObj){
        String friendUsername = friendObj.getFriendUsername();
        String curUsername = friendObj.getCurUsername();
        FriendObj existingFriend = friendObjInterface.findByCurUsernameAndFriendUsername(curUsername,friendUsername);
        if(friendObj.getfriendStatus()==null){
            friendObj.setfriendStatus(true);
        }
        if (existingFriend != null) {
            // If a similar friend already exists, return it without saving a new one
            return existingFriend;
        }
        if (friendUsername == null)
            return null;

        friendObjInterface.save(friendObj);
        return friendObj;

    }
//change to just recive user name in url
    @Operation(summary = "deletes given friend object from database")
    @ApiResponse(responseCode = "200", description = "Successfully deleted object", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = FriendObj.class)) })
    @DeleteMapping(path = "/deleteFriend/{passedcurUsername}/{passedfriendUsername}")
    String deleteFriend(@PathVariable String passedcurUsername,@PathVariable String passedfriendUsername){
       // String passedfriendUsername = passedFriendObj.getFriendUsername();
       // String passedcurUsername = passedFriendObj.getCurUsername();
        FriendObj existingFriendObj = friendObjInterface.findByCurUsernameAndFriendUsername(passedcurUsername, passedfriendUsername);
        if (existingFriendObj == null) {
            return null;
        }

        friendObjInterface.delete(existingFriendObj);
        return "deleted";

    }
    @Operation(summary = "update friend status ")
    @ApiResponse(responseCode = "200", description = "Successfully updated status", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = FriendObj.class)) })
    @PutMapping(path = "/updateStatus")
    FriendObj updateStatus(@RequestBody FriendObj passedFriendObj){
        String passedfriendUsername = passedFriendObj.getFriendUsername();
        String passedcurUsername = passedFriendObj.getCurUsername();
        Boolean newStatus = passedFriendObj.getfriendStatus();

        FriendObj existingFriendObj = friendObjInterface.findByCurUsernameAndFriendUsername(passedcurUsername, passedfriendUsername);

        if (existingFriendObj != null) {
            // Update the status
            existingFriendObj.setfriendStatus(newStatus);

            // Save the updated object back to the database
            FriendObj updatedFriendObj = friendObjInterface.save(existingFriendObj);

            return updatedFriendObj;
        } else {
            // Handle the case where the object is not found
            return null;
        }
    }
    @Operation(summary = "returns list of users who want to be friends the the current user")
    @ApiResponse(responseCode = "200", description = "Successfully sent list", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = FriendObj.class)) })
    @GetMapping(path = "/friendRequests/{username}")
    List<FriendObj> friendRequests(@PathVariable String username){
        ///String username = Justusername.getCurUsername();
        //List<FriendObj>  trueFriends = null;
        List<FriendObj>  friends =friendObjInterface.findByFriendUsername(username);

        friends.removeIf(friend -> friend.getfriendStatus());
        return friends;
    }



}
