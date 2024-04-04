package coms309;

import coms309.Comment.Comment;
import coms309.Comment.CommentsRepository;
import coms309.Friends.FriendObj;
import coms309.Friends.FriendObjInterface;
import coms309.Statistics.Statistics;
import coms309.Statistics.StatisticsRepository;
import coms309.Users.User;
import coms309.Users.UserInterface;
import coms309.Pin.Pin;
import coms309.Pin.PinRepository;
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
            if (userInterface.count() == 0) {
                User user1 = new User("bossf", "123");
                User user2 = new User("Jane", "jane@somemail.com");

                userInterface.save(user1);
                userInterface.save(user2);
            }
        };
    }
    @Bean
    CommandLineRunner initFriend(FriendObjInterface friendObjInterface) {
        return args -> {
            if (friendObjInterface.count() == 0) {
                FriendObj friendObj1 = new FriendObj("bossf", "zach",true);
                FriendObj friendObj2 = new FriendObj("Jane", "poop",true);
                FriendObj friendObj3 = new FriendObj("bossf", "stinky",true);

                friendObjInterface.save(friendObj1);
                friendObjInterface.save(friendObj2);
                friendObjInterface.save(friendObj3);
            }
        };
    }

    @Bean
    CommandLineRunner initPin(PinRepository pinRepository) {
        return args -> {
            if(pinRepository.count() == 0) {
                Pin zerozero = new Pin(0.005, 0.0, "Zero Zero Error Point");
                pinRepository.save(zerozero);
                Pin parkslibrary = new Pin(42.0281254, -93.6678685, "Parks Library");
                pinRepository.save(parkslibrary);
                Pin aidanapartment = new Pin(42.0351026, -93.6425714, "Aidan's Apartment");
                pinRepository.save(aidanapartment);
            }
        };
    }

    @Bean
    CommandLineRunner initComments(CommentsRepository commentsRepository, PinRepository pinRepository) {
        return args -> {
            if(commentsRepository.count() == 0) {
                Comment example = new Comment("First!", pinRepository.findById(1));
                commentsRepository.save(example);
            }
        };
    }

    @Bean
    CommandLineRunner initStatistics(StatisticsRepository statisticsRepository) {
        return args -> {
            if (statisticsRepository.count() == 0) {
                Statistics example = new Statistics(1);
                Statistics example2 = new Statistics(2);
                statisticsRepository.save(example);
                statisticsRepository.save(example2);
            }
        };
    }

}
