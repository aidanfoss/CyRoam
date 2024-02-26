package coms309;

import coms309.Users.User;
import coms309.Users.UserInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * PetClinic Spring Boot Application.
 * 
 * @author Vivek Bengre
 */

@SpringBootApplication
public class Application {
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    CommandLineRunner initUser(UserInterface userInterface) {
        return args -> {
            User user1 = new User("bossf", "123");
            User user2 = new User("Jane", "jane@somemail.com");

            userInterface.save(user1);
            userInterface.save(user2);
        };
    }

}
