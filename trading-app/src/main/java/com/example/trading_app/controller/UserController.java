package com.example.trading_app.controller;

import com.example.trading_app.model.User;
import com.example.trading_app.repository.UserRepository;

import java.util.HashMap;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // Allow cross-origin requests from React
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Signup endpoint
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "Username already exists";
        }
        // Hash the password before saving
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return "User created successfully";
    }

    // Login endpoint (simple version – in production use proper authentication)
    @PostMapping("/login")
        public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            response.put("loginSuccessful", false);
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        // Compare the hashed password
        if (BCrypt.checkpw(user.getPassword(), existingUser.getPassword())) {
            response.put("loginSuccessful", true);
            response.put("user", existingUser);
            return ResponseEntity.ok(response);
        } else {
            response.put("loginSuccessful", false);
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
