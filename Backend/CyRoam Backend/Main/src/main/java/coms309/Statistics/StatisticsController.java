package coms309.Statistics;

import java.util.List;

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
class StatisticsController {
    @Autowired
    public StatisticsRepository statisticsRepository;

    @Operation(summary = "Gets a list of all Statistics objects")
    @ApiResponse(responseCode = "200", description = "Successfully returned all Statistics objects", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Statistics.class)) })
    @GetMapping(path = "/Statistics")
    List<Statistics> getAllStatistics() {
        return statisticsRepository.findAll();
    }

    @Operation(summary = "Get a Statistics object by its id")
    @ApiResponse(responseCode = "200", description = "Successfully returned Statistics object", content = { @Content(mediaType = "json",
            schema = @Schema(implementation = Statistics.class)) })
    @GetMapping(path = "/Statistics/{id}")
    Statistics getStatisticsById(@Parameter(description = "id of Statistics object to be searched") @PathVariable int id) {
        return statisticsRepository.findById(id);
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
