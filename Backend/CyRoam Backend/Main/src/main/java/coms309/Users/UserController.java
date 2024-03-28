package coms309.Users;

import com.fasterxml.jackson.databind.util.JSONPObject;
import coms309.Progress.Progress;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.lang.Object;

@RestController
public class UserController {


    @Autowired
    UserInterface userInterface;


    //pottentially connect to friends list but might actually be the other way around
    private String success = "{\"message\":\"success ( :\"}";
    private String failure = "{\"message\":\"failure ) :\"}";
    @Operation(summary = "Gets a list of all users")
    @ApiResponse(responseCode = "200", description = "Successfully returned all friend users", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Progress.class)) })
    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userInterface.findAll();
    }
    @Operation(summary = "Gets user by uId")
    @ApiResponse(responseCode = "200", description = "Successfully returned user", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Progress.class)) })
    @GetMapping(path = "/users/{uId}")
    User getUserByUId( @PathVariable int uId){

        return userInterface.findByuId(uId);
    }

    @Operation(summary = "create new user")
    @ApiResponse(responseCode = "200", description = "Successfully returned all friend objects", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Progress.class)) })
    //Use this when creating a new user, provide username and password via json here
    @PostMapping(path = "/users")
    User createUser(@RequestBody User user){

        if (user == null)
            return null;
        userInterface.save(user);
        return user;
    }

    //for password/username check
    //GET requests can not have bodies change it to have information in url
    @Operation(summary = "checks entered username and pasword to if they are a user")
    @ApiResponse(responseCode = "200", description = "Successfully checked username and password", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Progress.class)) })
    @GetMapping(path = "/userCheck/{user}/{password}")
    UserCheck checkUser(@PathVariable String user, @PathVariable String password){
        UserCheck isUSerT = new UserCheck(true);
        UserCheck isUSerF = new UserCheck(false);
        if (user == null) {

            return isUSerF;
        }
        //String userName = user.getUsername();

        if(userInterface.findByUsername(user)==null){

            return isUSerF;
        }
        User actual = userInterface.findByUsername(user);

        //test if this actually works
        if(Objects.equals(actual.getPassword(), password)){

            return isUSerT;

        }
        else{

            return isUSerF;
        }


    }

}
