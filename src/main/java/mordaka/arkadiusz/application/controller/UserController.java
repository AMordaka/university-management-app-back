package mordaka.arkadiusz.application.controller;

import mordaka.arkadiusz.application.payload.SignUpRequest;
import mordaka.arkadiusz.application.payload.UserProfile;
import mordaka.arkadiusz.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        return userService.getUserProfile(username);
    }

    @GetMapping("/users")
    public List<UserProfile> getUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id) {
        return userService.deleteUserById(id);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody SignUpRequest signUpRequest) {
        return userService.updateUser(signUpRequest);
    }

    @PostMapping("/updateUserByAdmin")
    public ResponseEntity<?> updateUserByAdmin(@RequestBody SignUpRequest signUpRequest) {
        return userService.updateUserByAdmin(signUpRequest);
    }
}
