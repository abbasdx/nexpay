package com.nexpay.userservice.controller;

import com.nexpay.userservice.dto.JwtResponse;
import com.nexpay.userservice.dto.LoginRequest;
import com.nexpay.userservice.dto.SignupRequest;
import com.nexpay.userservice.entity.User;
import com.nexpay.userservice.repository.UserRepository;
import com.nexpay.userservice.service.UserService;
import com.nexpay.userservice.utils.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JWTUtil jwtUtil,
                          UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {

        // Prevent duplicate user registration
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("⚠️ User already exists");
        }

        // Create a new user from the signup request
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save the user and create an associated wallet
        User savedUser = userService.createUser(user);

        return ResponseEntity.ok("✅ User Registered Successfully: Id- " + savedUser.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // Verify that the user exists
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("❌ User Not Found!");
        }

        User user = userOpt.get();

        // Validate the provided password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("❌ Invalid Credentials");
        }

        // Add user role to JWT claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());

        // Generate JWT token for the authenticated user
        String token = jwtUtil.generateToken(claims, user.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }
}