package coms309.Friends;

import coms309.Statistics.Statistics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    //change to contain info in url
    @Operation(summary = "Gets a list of all friend objects that have the entered username as the current username")
    @ApiResponse(responseCode = "200", description = "Successfully returned all friend objects", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = FriendObj.class)) })
    @GetMapping(path = "/friends/{username}")

    List<FriendObj> requestFriends(@PathVariable String username){
        ///String username = Justusername.getCurUsername();
        List<FriendObj> f =friendObjInterface.findByCurUsername(username);
        return f;
    }


    @Operation(summary = "creates new friend object")
    @ApiResponse(responseCode = "200", description = "Successfully Created Object", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = FriendObj.class)) })
    @PostMapping(path = "/addFriend")

    FriendObj addFriend(@RequestBody FriendObj friendObj){
        String friendUsername = friendObj.getFriendUsername();
        if (friendUsername == null)
            return null;
        friendObjInterface.save(friendObj);
        return friendObj;

    }

    @Operation(summary = "deletes given friend object from database")
    @ApiResponse(responseCode = "200", description = "Successfully deleted object", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = FriendObj.class)) })
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
    @Operation(summary = "update friend status ")
    @ApiResponse(responseCode = "200", description = "Successfully updated status", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = FriendObj.class)) })
    @PutMapping(path = "/updateStatus")
    FriendObj updateStatus(@RequestBody FriendObj passedFriendObj){
        String passedfriendUsername = passedFriendObj.getFriendUsername();
        String passedcurUsername = passedFriendObj.getCurUsername();
        Boolean newStatus = passedFriendObj.getfriendStatus();

        friendObjInterface.findByCurUsernameOne(passedfriendUsername).setfriendStatus(newStatus);
        if(friendObjInterface.findByCurUsernameOne(passedfriendUsername).equals(passedFriendObj)) {
            return passedFriendObj;
        }
        else{
            return null;
        }
    }
}
