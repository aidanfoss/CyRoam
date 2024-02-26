package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    //Changed welcome message to encourage users to go to yippee page
    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309. Go to http://localhost:8080/yippee";
    }

//    @GetMapping("/{name}")
//    public String welcome(@PathVariable String name) {
//        return "Hello and welcome to COMS 309: " + name;
//    }

    //Added new page that says yippee
    @GetMapping("/yippee")
    public String yippee() {
        return "YYYYIIIIIIIIIPPPPPPPPPPPPEEEEEEEEEEEEE!!!!!!!!!";
    }
}
