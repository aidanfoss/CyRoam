package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {
    //testing testing again
    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309 CySplore will be the best App!!!";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309, this person is the best------> " + name;
    }
}
