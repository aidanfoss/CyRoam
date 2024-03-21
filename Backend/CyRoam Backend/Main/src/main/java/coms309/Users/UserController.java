package coms309.Users;

import com.fasterxml.jackson.databind.util.JSONPObject;
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

    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userInterface.findAll();
    }

    @GetMapping(path = "/users/{uId}")
    User getUserByUId( @PathVariable int uId){

        return userInterface.findByuId(uId);
    }


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
