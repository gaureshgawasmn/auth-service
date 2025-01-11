package com.techlabs.service.auth.controller;

import com.techlabs.service.auth.AuthRequest;
import com.techlabs.service.auth.entity.User;
import com.techlabs.service.auth.repository.UserRepo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.techlabs.service.auth.entity.User.hashPassword;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepo userRepo;

    public AuthController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticate(@RequestBody @Valid AuthRequest authRequest) {
        Optional<User> userOptional = userRepo.findByEmail(authRequest.getUsername());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        User user = userOptional.get();
        if (!user.getPasswordHash().equals(hashPassword(authRequest.getPassword()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
