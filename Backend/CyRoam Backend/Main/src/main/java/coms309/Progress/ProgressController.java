package coms309.Progress;

import java.util.List;

import com.fasterxml.jackson.databind.util.JSONPObject;
import coms309.Pin.Pin;
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
class ProgressController {
    @Autowired
    public ProgressRepository progressRepository;

    @Operation(summary = "Gets a list of all Progress objects")
    @ApiResponse(responseCode = "200", description = "Successfully returned all Progress objects", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Progress.class)) })
    @GetMapping(path = "/progress")
    List<Progress> getAllProgress() {
        return progressRepository.findAll();
    }

    @Operation(summary = "Get a Progress object by its id")
    @ApiResponse(responseCode = "200", description = "Successfully returned Progress object", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Progress.class)) })
    @GetMapping(path = "/progress/{id}")
    Progress getProgressById(@Parameter(description = "id of Progress object to be searched") @PathVariable int id) {
        return progressRepository.findById(id);
    }

    @Operation(summary = "Create a Progress object")
    @ApiResponse(responseCode = "200", description = "Successfully created Progress object", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Progress.class)) })
    @PostMapping(path = "/progress")
        Progress discoveredPin(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "userId, pinId, and discovered value of Progress" +
            " object to be created") @RequestBody Progress progress) {
            progressRepository.save(progress);
            return progress;
        }

}
