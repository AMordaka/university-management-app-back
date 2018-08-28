package mordaka.arkadiusz.application.controller;

import mordaka.arkadiusz.application.payload.ItemProfile;
import mordaka.arkadiusz.application.payload.UserProfile;
import mordaka.arkadiusz.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        return userService.getUserProfile(username);
    }

    @GetMapping("/student/{username}/items")
    public List<ItemProfile> getParticipatesItems(@PathVariable(value = "username") String username) {
        return userService.getParticipatesItems(username);
    }

    @GetMapping("/teacher/{username}/items")
    public List<ItemProfile> getCarriedItems(@PathVariable(value = "username") String username) {
        return userService.getCarriedItems(username);
    }

    @GetMapping("/users")
    public List<UserProfile> getUsers() {
        return userService.getAllUsers();
    }

}
