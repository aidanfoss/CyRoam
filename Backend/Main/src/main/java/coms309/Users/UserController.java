package coms309.Users;

import com.fasterxml.jackson.databind.node.ObjectNode;
import coms309.Discovery.DiscoveryRepository;
import coms309.Statistics.Statistics;
import coms309.Statistics.StatisticsRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {


    @Autowired
    UserInterface userInterface;

    @Autowired
    DiscoveryRepository discoveryRepository;

    @Autowired
    public StatisticsRepository statisticsRepository;

    //pottentially connect to friends list but might actually be the other way around
    private String success = "{\"message\":\"success ( :\"}";
    private String failure = "{\"message\":\"failure ) :\"}";
    @Operation(summary = "Gets a list of all users")
    @ApiResponse(responseCode = "200", description = "Successfully returned all friend users", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = User.class)) })
    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userInterface.findAll();
    }
    @Operation(summary = "Gets user by uId")
    @ApiResponse(responseCode = "200", description = "Successfully returned user", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = User.class)) })
    @GetMapping(path = "/users/{uId}")
    User getUserByUId( @PathVariable int uId){

        //every time this is called update/create statistics row every time this called

        return userInterface.findByuId(uId);
    }

    @Operation(summary = "create new user")
    @ApiResponse(responseCode = "200", description = "Successfully returned all friend objects", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = User.class)) })
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
    @Operation(summary = "checks entered username and pasword to see if they are a user")
    @ApiResponse(responseCode = "200", description = "Successfully checked username and password", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = User.class)) })
    @PostMapping (path = "/userCheck")
    UserCheck checkUser(@RequestBody User user){
        String userN = user.getUsername();
        String password = user.getPassword();
        UserCheck isUSerF = new UserCheck();
        if (userN == null) {

            return isUSerF;
        }


        if(userInterface.findByUsername(userN)==null){

            return isUSerF;
        }

        User actual = userInterface.findByUsername(userN);
        //permissions needs to be added to the user obj / table
        UserCheck isUSerT = new UserCheck(actual.getuId(), actual.getUsername(), true, actual.getPermissions(), actual.getScore(), "correct");
        if(userInterface.findByUsername(userN).getPermissions()==-2){
            return new UserCheck(actual.getuId(), actual.getUsername(), false, actual.getPermissions(), actual.getScore(), "banned User");
        }

        if(Objects.equals(actual.getPassword(), password)){

            return isUSerT;

        }
        else{

            return isUSerF;
        }


    }
    @Operation(summary = "send list of top 5 users")
    @ApiResponse(responseCode = "200", description = "list sent", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = User.class)) })
    @GetMapping(path = "/leaderBoard")
    List<UserScore> leaderBoard() {

        List<UserScore> userScoreList = new ArrayList<>();
        List<User> users = userInterface.findAll();

        for(User user : users){
            String username = user.getUsername();
            if(user.getStatistics()!=null) {
                user.setScore(statisticsRepository.findByUser(user).getScore());
            }
            if(user.getPermissions()>=0) {
                int score = user.getScore(); // change this to grab amount of pins discovered
                UserScore userScoreObj = new UserScore(username, score);
                userScoreList.add(userScoreObj);
            }
        }

        // Sort userScoreList based on scores in descending order
        userScoreList.sort((u1, u2) -> Integer.compare(u2.getScore(), u1.getScore()));

        return userScoreList;
    }

    @Operation(summary = "promote/demote user to specified level")
    @ApiResponse(responseCode = "200", description = "user promoted", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = User.class)) })

    @PutMapping(path = "/userPerms")
    User setPermissions(@RequestBody ObjectNode objectNode){
        String username = objectNode.get("username").asText();
        int promotion = objectNode.get("promotion").asInt();
        if (username == null)
            return null;

        User s = userInterface.findByUsername(username);
        if(promotion>=0 && s.getPermissions()==-3){
            return null;
        }

        s.setPermissions(promotion);
        userInterface.save(s);
        //userInterface.save(user);
        return userInterface.findByUsername(username);
    }
    @Operation(summary = "promote/demote user to specified level as a admin")
    @ApiResponse(responseCode = "200", description = "user promoted", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = User.class)) })

    @PutMapping(path = "/userPermsAdmin")
    User setPermissionsAdmin(@RequestBody ObjectNode objectNode){
        String username = objectNode.get("username").asText();
        int promotion = objectNode.get("promotion").asInt();
        if (username == null)
            return null;

        User s = userInterface.findByUsername(username);

        s.setPermissions(promotion);
        userInterface.save(s);
        //userInterface.save(user);
        return userInterface.findByUsername(username);
    }
    @Operation(summary = "set user score")
    @ApiResponse(responseCode = "200", description = "user promoted", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = User.class)) })

    @PutMapping(path = "/setScore")
    User setScore(@RequestBody ObjectNode objectNode){
        String username = objectNode.get("username").asText();
        int score = objectNode.get("score").asInt();
        if (username == null)
            return null;
        User s = userInterface.findByUsername(username);
        s.setScore(score);
        userInterface.save(s);
        //userInterface.save(user);
        return userInterface.findByUsername(username);
    }
}
