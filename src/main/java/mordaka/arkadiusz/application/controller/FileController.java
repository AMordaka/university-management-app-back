package mordaka.arkadiusz.application.controller;

import mordaka.arkadiusz.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {

    private final UserService userService;

    public FileController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/addavatar/{username}", method = RequestMethod.POST)
    public ResponseEntity<?> addAvatar(@PathVariable(value = "username") String username, @RequestPart(name = "file", required = false) MultipartFile file) {
        return userService.addAvatar(username, file);
    }

    @GetMapping("/getavatar/{username}")
    public ResponseEntity<?> getAvatar(@PathVariable(value = "username") String username) {
        return userService.getAvatar(username);
    }
}
