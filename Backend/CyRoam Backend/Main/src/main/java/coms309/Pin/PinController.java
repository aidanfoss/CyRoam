package coms309.Pin;

import java.util.ArrayList;
import java.util.List;

import coms309.Users.UserInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class PinController {

    @Autowired
    PinRepository pinRepository;

    private static UserInterface userRepository;
    @Autowired
    public void setUserRepository(UserInterface repo) {userRepository = repo;}

    @Operation(summary = "Get a list of all Pins")
    @ApiResponse(responseCode = "200", description = "Successfully returned all Pins", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Pin.class)) })
    @GetMapping(path = "/user/{id}/pins")
    List<PinPair> getAllPins(@PathVariable int id) {
        List<PinPair> listPair = new ArrayList<>();
        boolean isDiscovered = true;
        for (int i = 0; i < pinRepository.count(); i++) {
            if(userRepository.findByuId(id).getPins().contains(pinRepository.findAll().get(i))) {
                isDiscovered = true;
            } else {
                isDiscovered = false;
            }
            PinPair temp = new PinPair(pinRepository.findAll().get(i), isDiscovered);
            listPair.add(temp);
        }


        return listPair;
    }
    @Operation(summary = "Get a Pin by its id")
    @ApiResponse(responseCode = "200", description = "Found the Pin", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Pin.class)) })
    @GetMapping(path = "/pins/{id}")
    Pin getPinById(@Parameter(description = "id of Pin to be searched") @PathVariable int id){
        return pinRepository.findById(id);
    }

    @Operation(summary = "Create a Pin")
    @ApiResponse(responseCode = "200", description = "Created the Pin", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Pin.class)) })
    @PostMapping(path = "/pins")
    Pin createPin(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "x, y, and name of Pin to be created") @RequestBody Pin pin) {
        if (pin == null) {
            return null;
        }
        pinRepository.save(pin);
        return pin;
    }

    @Operation(summary = "Delete a Pin")
    @ApiResponse(responseCode = "200", description = "Deleted the Pin", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Pin.class)) })
    @DeleteMapping(path = "/pins/{id}")
    Pin deletePinById(@Parameter(description = "id of Pin to be deleted") @PathVariable int id) {
        Pin tempPin = pinRepository.findById(id);
        pinRepository.deleteById(id);
        return tempPin;
    }

    //Deletes all pins given an array of pin id's.
    @Operation(summary = "Delete all Pins in a given array")
    @ApiResponse(responseCode = "200", description = "Deleted the given Pins", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Pin.class)) })
    @DeleteMapping(path = "/pins")
    List<Pin> deleteMultiPins(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Array of the ids of the Pins to be deleted") @RequestBody List<Integer> ids) {
        List<Pin> deletedPins = new ArrayList<>();
        for (int i : ids) {
            deletedPins.add(pinRepository.findById(i));
            pinRepository.deleteById(i);
        }
        return deletedPins;
    }

}
