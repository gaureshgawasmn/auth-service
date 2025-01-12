package com.techlabs.service.auth.controller;

import com.techlabs.service.auth.AuthRequest;
import com.techlabs.service.auth.AuthUser;
import com.techlabs.service.auth.entity.User;
import com.techlabs.service.auth.mapper.UserMapper;
import com.techlabs.service.auth.repository.UserRepo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.techlabs.service.auth.entity.User.hashPassword;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final UserMapper userMapper = UserMapper.MAPPER;
    private final UserRepo userRepo;

    public AuthController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/authenticate")
    public AuthUser authenticate(@RequestBody @Valid AuthRequest authRequest) {
        Optional<User> userOptional = userRepo.findByEmail(authRequest.getUsername());
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        User user = userOptional.get();
        if (!user.getPasswordHash().equals(hashPassword(authRequest.getPassword()))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized");
        }
        return userMapper.userToAuthUser(user);
    }
}
