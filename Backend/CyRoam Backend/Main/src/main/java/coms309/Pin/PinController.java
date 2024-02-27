package coms309.Pin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PinController {

    @Autowired
    public PinRepository pinRepository;

    @GetMapping(path = "/pins")
    List<Pin> getAllPins() {
        Pin lakelaverne = new Pin(42.023949, -93.647595, "Lake LaVerne");
        Pin zerozero = new Pin(0.005, 0.0, "Zero Zero");
        pinRepository.save(lakelaverne);
        pinRepository.save(zerozero);
        return pinRepository.findAll();
    }

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
        return "Pin with id " + pin.getId() + " created at (" + pin.getX() + ", " + pin.getY() + ")";
    }

}
