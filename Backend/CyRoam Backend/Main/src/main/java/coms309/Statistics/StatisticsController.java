package coms309.Statistics;

import java.util.List;

import coms309.Discovery.DiscoveryRepository;
import coms309.Fog.Fog;
import coms309.FogDiscovery.FogDiscoveryRepository;
import coms309.Pin.Pin;
import coms309.Users.User;
import coms309.Users.UserInterface;
import coms309.Users.UserScore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class StatisticsController {
    @Autowired
    public StatisticsRepository statisticsRepository;

    @Autowired
    DiscoveryRepository discoveryRepository;

    @Autowired
    UserInterface userInterface;

    @Autowired
    FogDiscoveryRepository fogDiscoveryRepository;

    @Operation(summary = "Gets a list of all Statistics objects")
    @ApiResponse(responseCode = "200", description = "Successfully returned all Statistics objects", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Statistics.class)) })
    @GetMapping(path = "/Statistics")
    List<Statistics> getAllStatistics() {
        return statisticsRepository.findAll();
    }

    @Operation(summary = "Get a Statistics object by its uId")
    @ApiResponse(responseCode = "200", description = "Successfully returned Statistics object", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Statistics.class)) })
    @GetMapping(path = "/Statistics/{uId}")
    Statistics getStatisticsById(@Parameter(description = "uId of Statistics object to be searched") @PathVariable int uId) {

        User user = userInterface.findByuId(uId);
        Statistics stats;
        List<User> users = userInterface.findAll();
        List<Fog> fogdiscovered = fogDiscoveryRepository.findFogByUser(uId);
        int fogs = fogdiscovered.size();
        if(user.getStatistics()==null){
           //create stats obj
            stats = new Statistics();
           //connect stats entry to user
           stats.setUser(user);

           //get list of pins discovered
           List<Pin> pinsDiscovered = discoveryRepository.findPinsByUser(uId);
           //set pins discovered
           stats.setNumDiscoveredPins(pinsDiscovered.size());
           //find rank
            users.sort((u1, u2) -> Integer.compare(u2.getScore(), u1.getScore()));
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getuId() == uId) {
                    stats.setRank(i+1);
                    break;// return the index of the found user
                }
            }

            //find fog discovered
            stats.setFogDiscovered(fogs);


           user.setStatistics(stats);

        }
       else{
           //create stats obj
            stats = statisticsRepository.findByUser(user);
            //find rank
            users.sort((u1, u2) -> Integer.compare(u2.getScore(), u1.getScore()));
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getuId() == uId) {
                    stats.setRank(i+1);
                    break;// return the index of the found user
                }
            }
           //get list of pins discovered
           List<Pin> pinsDiscovered = discoveryRepository.findPinsByUser(uId);
           //set pins discovered
           stats.setNumDiscoveredPins(pinsDiscovered.size());
           //set fog discoverd
            stats.setFogDiscovered(fogs);

            //set score
            stats.setScore(stats.getFogDiscovered()/5 + stats.getNumDiscoveredPins()*5);
            user.setScore(stats.getFogDiscovered()/5 + stats.getNumDiscoveredPins()*5);
        }

        statisticsRepository.save(stats);

        return statisticsRepository.findByUser(user);
    }



    @Operation(summary = "Create a Statistics object")
    @ApiResponse(responseCode = "200", description = "Successfully created Statistics object", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Statistics.class)) })
    @PostMapping(path = "/Statistics")
    Statistics discoveredPin(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "userId, pinId, and discovered value of Statistics" +
            " object to be created") @RequestBody Statistics statistics) {
            statisticsRepository.save(statistics);
            return statistics;
        }


    }
