package com.severalabs.ias.controller;

import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.LoginRequest;
import com.severalabs.ias.dto.SignUpRequest;
import com.severalabs.ias.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<User> createUser (@RequestBody SignUpRequest signUpRequest) {
        return new ResponseEntity<>(
                userService.createUser(signUpRequest), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin (@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(
                userService.loginUser(loginRequest), HttpStatusCode.valueOf(200));
    }
}
