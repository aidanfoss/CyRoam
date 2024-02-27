package ProgressTable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        String discoveredPin(@RequestBody Progress progress) {
            progressRepository.save(progress);
            return "User with id " + progress.getUserId() + " discovered pin " + progress.getPinId();
        }

}
