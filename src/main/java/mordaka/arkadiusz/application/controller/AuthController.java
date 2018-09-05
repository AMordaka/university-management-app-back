package mordaka.arkadiusz.application.controller;

import mordaka.arkadiusz.application.payload.LoginRequest;
import mordaka.arkadiusz.application.payload.SignUpRequest;
import mordaka.arkadiusz.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userService.registerStudent(signUpRequest);
    }

    @PostMapping("/signupTeacher")
    public ResponseEntity<?> registerTeacher(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userService.registerTeacher(signUpRequest);
    }
}
