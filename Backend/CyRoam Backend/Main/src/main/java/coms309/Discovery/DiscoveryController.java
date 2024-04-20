package coms309.Discovery;

import coms309.Pin.Pin;
import coms309.Users.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiscoveryController {
    @Autowired
    DiscoveryRepository discoveryRepository;

    @Operation(summary = "Get all Pins a User has discovered")
    @ApiResponse(responseCode = "200", description = "Found the Pins", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Discovery.class)) })
    @GetMapping(path = "/users/{id}/pins")
    List<Pin> getPinByUser(@Parameter(description = "id of User") @PathVariable long id){
        return discoveryRepository.findPinsByUser(id);
    }

    @Operation(summary = "Create a Discovery")
    @ApiResponse(responseCode = "200", description = "Created the Discovery", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Pin.class)) })
    @PostMapping(path = "/discovery")
    Discovery createDiscovery(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User and Pin that the User discovered") @RequestBody Pin pin, User user) {
        Discovery discovery = new Discovery(user, pin);
        discoveryRepository.save(discovery);
        return discovery;
    }
}
