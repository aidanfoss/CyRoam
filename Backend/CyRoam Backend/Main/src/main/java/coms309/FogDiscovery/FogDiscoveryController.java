package coms309.FogDiscovery;

import coms309.Discovery.Discovery;
import coms309.Fog.Fog;
import coms309.Fog.FogRepository;
import coms309.Pin.Pin;
import coms309.Users.UserInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FogDiscoveryController {
    @Autowired
    FogDiscoveryRepository fogDiscoveryRepository;

    @Autowired
    FogRepository fogRepository;

    @Autowired
    UserInterface userInterface;

    @Operation(summary = "Get all Fog a User has cleared")
    @ApiResponse(responseCode = "200", description = "Found the Fog")
    @GetMapping(path = "/users/{id}/fog")
    List<Fog> getFogByUser(@Parameter(description = "id of the User") @PathVariable long id){
        return fogDiscoveryRepository.findFogByUser(id);
    }

    @Operation(summary = "Create a fogDiscovery (Clear this fog for a User)")
    @ApiResponse(responseCode = "200", description = "Created the fogDiscovery")
    @PostMapping(path = "/users/{uId}/fogDiscovery/{fId}")
    String createFogDiscovery(@Parameter(description = "Id of the User") @PathVariable int uId, @Parameter(description = "Id of the Fog") @PathVariable int fId) {
        FogDiscovery discovery = new FogDiscovery(userInterface.findByuId(uId), fogRepository.findById(fId));
        fogDiscoveryRepository.save(discovery);
        return "User " + uId + " has cleared Fog " + fId;
    }

    @Operation(summary = "Checks if a User has cleared a specific Fog")
    @ApiResponse(responseCode = "200", description = "Returned successfully")
    @GetMapping(path = "/users/{uId}/hasCleared/{fId}")
    Boolean hasCleared(@Parameter(description = "Id of the User") @PathVariable int uId, @Parameter(description = "Id of the Fog") @PathVariable int fId) {
        return getFogByUser(uId).contains(fogRepository.findById(fId));
    }

    @Operation(summary = "Returns all Fog a user has not cleared")
    @GetMapping(path = "/users/{uId}/hasNotCleared")
    List<Fog> hasNotCleared(@Parameter(description = "Id of the User") @PathVariable int uId) {
        List<Fog> undiscovered = new ArrayList<>();
        List<Fog> discovered = fogDiscoveryRepository.findFogByUser(uId);
        List<Fog> all = fogRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            if (!discovered.contains(all.get(i))) {
                undiscovered.add(all.get(i));
            }
        }
        return undiscovered;
    }
}
