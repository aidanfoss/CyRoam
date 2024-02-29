package coms309.Progress;

import java.util.List;

import com.fasterxml.jackson.databind.util.JSONPObject;
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

    @GetMapping(path = "/progress")
    List<Progress> getAllProgress() {
        return progressRepository.findAll();
    }
    @GetMapping(path = "/progress/{id}")
    Progress getProgressById(@PathVariable int id) {
        return progressRepository.findById(id);
    }

    @PostMapping(path = "/progress")
        Progress discoveredPin(@RequestBody Progress progress) {
            progressRepository.save(progress);
            return progress;
        }

}
