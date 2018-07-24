package mordaka.arkadiusz.application.service;

import mordaka.arkadiusz.application.payload.JwtAuthenticationResponse;
import mordaka.arkadiusz.application.payload.LoginRequest;
import mordaka.arkadiusz.application.payload.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    JwtAuthenticationResponse authenticateUser(LoginRequest request);

    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);
}
