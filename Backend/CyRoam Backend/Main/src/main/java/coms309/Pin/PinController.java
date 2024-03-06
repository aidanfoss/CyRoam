package coms309.Pin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PinController {

    @Autowired
    PinRepository pinRepository;

    @GetMapping(path = "/pins")
    List<Pin> getAllPins() {

        return pinRepository.findAll();
    }

    @GetMapping(path = "/pins/{id}")
    Pin getPinById(@PathVariable int id){
        return pinRepository.findById(id);
    }

    @PostMapping(path = "/pins")
    Pin createPin(@RequestBody Pin pin) {
        if (pin == null) {
            return null;
        }
        pinRepository.save(pin);
        return pin;
    }

    @DeleteMapping(path = "/pins/{id}")
    Pin deletePinById(@PathVariable int id) {
        Pin tempPin = pinRepository.findById(id);
        pinRepository.deleteById(id);
        return tempPin;
    }

    //Deletes all pins given an array of pin id's.
    @DeleteMapping(path = "/pins")
    List<Pin> deleteMultiPins(@RequestBody List<Integer> ids) {
        List<Pin> deletedPins = new ArrayList<>();
        for (int i : ids) {
            deletedPins.add(pinRepository.findById(i));
            pinRepository.deleteById(i);
        }
        return deletedPins;
    }
}
