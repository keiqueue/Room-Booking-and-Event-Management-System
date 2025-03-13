/**
 * COMP 3095 - Group Assignment
 * @Auhtors: Jam Furaque, Andrew Stewart, Kei Ishikawa, Carl Trinidad
 * */
package ca.gbc.userservice.service;

import ca.gbc.userservice.model.User;
import ca.gbc.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        if (userRepository.findByNameAndEmail(user.getName(), user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User userDetails){
        User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found"));
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        user.setUserType(userDetails.getUserType());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
