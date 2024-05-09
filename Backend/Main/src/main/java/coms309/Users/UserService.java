package coms309.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserInterface userRepository;

    public User createUser(User user) {
        // Add logic to handle user creation constraints like username uniqueness.
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Additional methods to update and delete users
}
