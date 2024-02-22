package coms309;

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
public class PinController {
    @Autowired
    PinRepository pinRepository;

    @GetMapping(path = "/pins")
    List<Pin> getAllPins() {return pinRepository.findAll();}

    @GetMapping(path = "/pins/{id}")
    Pin getLaptopById(@PathVariable int id){
        return pinRepository.findById(id);
    }

    @PostMapping(path = "/pins")
    String createPin(@RequestBody Pin pin) {
        if (pin == null) {
            return "Pin creation failed: Pin object given was null";
        }
        pinRepository.save(pin);
        return "Pin successfully created at " + pin.getX() + ", " + pin.getY();
    }

}
