package coms309.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    String createUser(@RequestBody User user){

        if (user == null)
            return failure;
        userInterface.save(user);
        return success;
    }

    //for password/username check
    @GetMapping(path = "/userCheck")
    Boolean checkUser(@RequestBody User userEntered){
        if (userEntered == null) {
            return false;
        }
        String userName = userEntered.getUsername();

        if(userInterface.findByUsername(userName)==null){
            return false;
        }
        User actual = userInterface.findByUsername(userName);

        if(Objects.equals(actual.getPassword(), userEntered.getPassword())){
            return true;
            //if returning string
            //return "welcome back "+ actual.getUserName() + "! uId:"+ + actual.getuId();
        }
        else{
            return false;
        }

        //return userInterface.findByuId(uId);
    }

}
