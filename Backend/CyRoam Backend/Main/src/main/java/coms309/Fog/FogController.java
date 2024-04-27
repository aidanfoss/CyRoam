package coms309.Fog;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FogController {
    @Autowired
    FogRepository fogRepository;

    @Operation(summary = "Get all Fog")
    @ApiResponse(responseCode = "200", description = "Found the Fog", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Fog.class)) })
    @GetMapping(path = "/fog")
    List<Fog> getAllFog(){
        return fogRepository.findAll();
    }

    @Operation(summary = "Create a Fog")
    @ApiResponse(responseCode = "200", description = "Created the Fog", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Fog.class)) })
    @PostMapping(path = "/fog")
    Fog createPin(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "x, y, and imagePath of Fog to be created") @RequestBody Fog fog) {
        if (fog == null) {
            return null;
        }
        fogRepository.save(fog);
        return fog;
    }
}
