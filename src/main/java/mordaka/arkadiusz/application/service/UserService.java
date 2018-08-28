package mordaka.arkadiusz.application.service;

import mordaka.arkadiusz.application.payload.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    JwtAuthenticationResponse authenticateUser(LoginRequest request);

    ResponseEntity<?> registerStudent(SignUpRequest signUpRequest);

    UserProfile getUserProfile(String username);

    List<ItemProfile> getParticipatesItems(String username);

    List<ItemProfile> getCarriedItems(String username);

    ResponseEntity<?> registerTeacher(SignUpRequest signUpRequest);

    List<UserProfile> getAllUsers();
}
