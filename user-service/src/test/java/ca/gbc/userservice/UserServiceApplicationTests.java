package ca.gbc.userservice;

import ca.gbc.userservice.model.User;
import ca.gbc.userservice.repository.UserRepository;
import ca.gbc.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setRole("Participant");
        user.setUserType("Student");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);
        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.getName());
        assertEquals("johndoe@example.com", savedUser.getEmail());
        assertEquals("Participant", savedUser.getRole());
        assertEquals("Student", savedUser.getUserType());
    }

    @Test
    void shouldGetUserById() {
        User user = new User();
        user.setName("Jane Doe");
        user.setEmail("janedoe@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1L);
        assertNotNull(foundUser);
        assertEquals("Jane Doe", foundUser.get().getName());
        assertEquals("janedoe@example.com", foundUser.get().getEmail());
    }
}
