package mordaka.arkadiusz.application.service.impl;

import mordaka.arkadiusz.application.exception.AppException;
import mordaka.arkadiusz.application.exception.ResourceNotFoundException;
import mordaka.arkadiusz.application.model.*;
import mordaka.arkadiusz.application.payload.*;
import mordaka.arkadiusz.application.repository.RoleRepository;
import mordaka.arkadiusz.application.repository.UserRepository;
import mordaka.arkadiusz.application.security.JwtTokenProvider;
import mordaka.arkadiusz.application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public ResponseEntity<?> registerStudent(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }
        Student student = new Student();
        User user = new User(signUpRequest.getName(), signUpRequest.getSurname(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword(),
                signUpRequest.getStreet(), signUpRequest.getNumberStreet(), signUpRequest.getPostalCode(), signUpRequest.getCity());
        user.setStudent(student);
        student.setUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_STUDENT)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @Override
    public ResponseEntity<?> registerTeacher(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }
        Teacher teacher = new Teacher();
        User user = new User(signUpRequest.getName(), signUpRequest.getSurname(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword(),
                signUpRequest.getStreet(), signUpRequest.getNumberStreet(), signUpRequest.getPostalCode(), signUpRequest.getCity());
        user.setTeacher(teacher);
        teacher.setUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_TEACHER)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @Override
    public List<UserProfile> getAllUsers() {
        List<UserProfile> userProfiles = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userProfiles.add(new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getCreatedAt(), user.getUpdatedAt(), user.getStreet(), user.getNumberStreet(), user.getPostalCode(), user.getCity(), user.getRoles()));
        }
        return userProfiles;
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public JwtAuthenticationResponse authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail());
        User user = userOptional.orElse(new User());
        return new JwtAuthenticationResponse(jwt, user.getName(), user.getSurname(), user.getRoles(), user.getUsername());
    }


    @Override
    public UserProfile getUserProfile(String username) {
        User user = findUser(username);
        return new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getCreatedAt(), user.getUpdatedAt(), user.getStreet(), user.getNumberStreet(), user.getPostalCode(), user.getCity(), user.getRoles());
    }

    @Override
    public User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }
}
