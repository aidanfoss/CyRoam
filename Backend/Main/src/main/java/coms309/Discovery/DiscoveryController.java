package coms309.Discovery;
import coms309.Pin.Pin;
import coms309.Pin.PinRepository;
import coms309.Users.User;
import coms309.Users.UserInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DiscoveryController {
    @Autowired
    DiscoveryRepository discoveryRepository;

    @Autowired
    PinRepository pinRepository;

    @Autowired
    UserInterface userInterface;

    @Operation(summary = "Get all Pins a User has discovered")
    @ApiResponse(responseCode = "200", description = "Found the Pins")
    @GetMapping(path = "/users/{id}/pins")
    List<Pin> getPinByUser(@Parameter(description = "id of User") @PathVariable long id){
        return discoveryRepository.findPinsByUser(id);
    }


    @Operation(summary = "Create a Discovery")
    @ApiResponse(responseCode = "200", description = "Created the Discovery")
    @PostMapping(path = "/users/{uId}/discovery/{pId}")
    String createDiscovery(@Parameter(description = "Id of the User") @PathVariable int uId, @Parameter(description = "Id of the Pin") @PathVariable int pId) {
        List<Pin> discovered = discoveryRepository.findPinsByUser(uId);
        for (int i = 0; i < discovered.size(); i++) {
            if (discovered.get(i).getId() == pId) {
                return "User " + uId + " has already discovered Pin " + pId;
            }
        }

        Discovery discovery = new Discovery(userInterface.findByuId(uId), pinRepository.findById(pId));
        discoveryRepository.save(discovery);
        return "User " + uId + " has discovered Pin " + pId;
    }

    @Operation(summary = "Checks if a User has discovered a specific Pin")
    @ApiResponse(responseCode = "200", description = "Returned successfully")
    @GetMapping(path = "/users/{uId}/hasDiscovered/{pId}")
    Boolean isDiscovered(@Parameter(description = "Id of the User") @PathVariable int uId, @Parameter(description = "Id of the Pin") @PathVariable int pId) {
        return getPinByUser(uId).contains(pinRepository.findById(pId));
    }

    @Operation(summary = "Finds all Pins a User has not discovered")
    @GetMapping(path = "/users/{uId}/hasNotDiscovered")
    List<Pin> isNotDiscovered(@Parameter(description = "Id of the User") @PathVariable int uId) {
        List<Pin> undiscovered = new ArrayList<>();
        List<Pin> discovered = discoveryRepository.findPinsByUser(uId);
        List<Pin> all = pinRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            if (!discovered.contains(all.get(i))) {
                undiscovered.add(all.get(i));
            }
        }
        return undiscovered;
    }
}