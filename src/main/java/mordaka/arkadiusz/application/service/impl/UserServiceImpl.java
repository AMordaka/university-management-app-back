package mordaka.arkadiusz.application.service.impl;

import mordaka.arkadiusz.application.exception.AppException;
import mordaka.arkadiusz.application.exception.ResourceNotFoundException;
import mordaka.arkadiusz.application.model.*;
import mordaka.arkadiusz.application.payload.*;
import mordaka.arkadiusz.application.repository.RoleRepository;
import mordaka.arkadiusz.application.repository.UserRepository;
import mordaka.arkadiusz.application.security.JwtTokenProvider;
import mordaka.arkadiusz.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;


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
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
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
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
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
        return new JwtAuthenticationResponse(jwt);
    }


    @Override
    public UserProfile getUserProfile(String username) {
        User user = findUser(username);
        return new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt());
    }

    @Override
    public List<ItemProfile> getParticipatesItems(String username) {
        User user = findUser(username);
        List<ItemProfile> itemProfiles = new ArrayList<>();
        for (Item item : user.getStudent().getItems()) {
            itemProfiles.add(new ItemProfile(item.getId(), item.getSubjectName(), item.getGrade(), item.getTeacher().getUser().getName()));
        }
        return itemProfiles;
    }

    @Override
    public List<ItemProfile> getCarriedItems(String username) {
        User user = findUser(username);
        List<ItemProfile> itemProfiles = new ArrayList<>();
        for (Item item : user.getTeacher().getCarriedItems()) {
            itemProfiles.add(new ItemProfile(item.getId(), item.getSubjectName(), item.getGrade(), item.getStudent().getUser().getName()));
        }
        return itemProfiles;
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }
}
