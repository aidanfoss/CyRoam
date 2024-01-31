package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple Hello World Controller to display the string returned
 *
 * @author Vivek Bengre
 */

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }


    String dummyPerson = null;
    @GetMapping("/person/create/{name}")
    public String name(@PathVariable String name) {
        dummyPerson = name;
        return "Dummy person created: " + name;
    }


    @GetMapping("/peoples") public String people() {
        return dummyPerson;
    }


}
