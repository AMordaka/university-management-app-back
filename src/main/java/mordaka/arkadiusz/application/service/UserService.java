package mordaka.arkadiusz.application.service;

import mordaka.arkadiusz.application.model.User;
import mordaka.arkadiusz.application.payload.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    JwtAuthenticationResponse authenticateUser(LoginRequest request);

    ResponseEntity<?> registerStudent(SignUpRequest signUpRequest);

    UserProfile getUserProfile(String username);

    ResponseEntity<?> registerTeacher(SignUpRequest signUpRequest);

    List<UserProfile> getAllUsers();

    ResponseEntity<?> deleteUserById(Long id);

    User findUser(String username);

    ResponseEntity<?> updateUser(SignUpRequest signUpRequest);

    ResponseEntity<?> updateUserByAdmin(SignUpRequest signUpRequest);

    List<User> findAllUsers();

    ResponseEntity<?> addAvatar(String index, MultipartFile file);

    ResponseEntity<?> getAvatar(String username);

}
