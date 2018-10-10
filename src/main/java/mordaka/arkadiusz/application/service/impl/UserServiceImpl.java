package mordaka.arkadiusz.application.service.impl;

import mordaka.arkadiusz.application.exception.AppException;
import mordaka.arkadiusz.application.exception.ResourceNotFoundException;
import mordaka.arkadiusz.application.model.*;
import mordaka.arkadiusz.application.payload.*;
import mordaka.arkadiusz.application.repository.AvatarRepository;
import mordaka.arkadiusz.application.repository.RoleRepository;
import mordaka.arkadiusz.application.repository.UserRepository;
import mordaka.arkadiusz.application.security.JwtTokenProvider;
import mordaka.arkadiusz.application.service.UserService;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public UserServiceImpl(AuthenticationManager authenticationManager, AvatarRepository avatarRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.avatarRepository = avatarRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }


    @Override
    public ResponseEntity<?> registerStudent(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Index is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Email Address already in use!"));
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
        userRepository.save(user);
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
    }

    @Override
    public ResponseEntity<?> registerTeacher(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Index is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Email Address already in use!"));
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
        userRepository.save(user);
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
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
    public ResponseEntity<?> deleteUserById(Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "User deleted successfully"));
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

    @Override
    public ResponseEntity<?> updateUser(SignUpRequest signUpRequest) {
        User user = userRepository.findByUsernameOrEmail(signUpRequest.getUsername(), signUpRequest.getEmail()).orElseThrow(() -> new AppException("Error"));
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setStreet(signUpRequest.getStreet());
        user.setNumberStreet(signUpRequest.getNumberStreet());
        user.setPostalCode(signUpRequest.getPostalCode());
        user.setCity(signUpRequest.getCity());
        userRepository.save(user);
        return ResponseEntity.ok().body(new ApiResponse(true, "User updated successfully"));
    }

    @Override
    public ResponseEntity<?> updateUserByAdmin(SignUpRequest signUpRequest) {
        User user = userRepository.findByUsernameOrEmail(signUpRequest.getUsername(), signUpRequest.getEmail()).orElseThrow(() -> new AppException("Error"));
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok().body(new ApiResponse(true, "User updated successfully"));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<?> addAvatar(String index, MultipartFile file) {
        try {
            if (file != null) {
                if (getAvatarFromUser(index) != null) {
                    avatarRepository.delete(getAvatarFromUser(index));
                }
                Avatar avatar = new Avatar();
                avatar.setUser(findUser(index));
                avatar.setImage(file.getBytes());
                avatar.setFileName(file.getName());
                avatarRepository.save(avatar);
                return ResponseEntity.ok("Avatar saved successfully");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No File!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }
    }

    @Override
    public ResponseEntity<?> getAvatar(String username) {
        User user = findUser(username);
        List<Avatar> list = avatarRepository.findAll();
        for (Avatar avatar : list) {
            if (avatar.getUser() == user) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                headers.setCacheControl(CacheControl.noCache().getHeaderValue());
                return new ResponseEntity<>(avatar.getImage(), headers, HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Avatar");
    }

    private Avatar getAvatarFromUser(String username) {
        User user = findUser(username);
        List<Avatar> list = avatarRepository.findAll();
        for (Avatar avatar : list) {
            if (avatar.getUser() == user) {
                return avatar;
            }
        }
        return null;
    }
}
