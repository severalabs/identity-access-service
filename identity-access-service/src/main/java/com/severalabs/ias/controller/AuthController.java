package com.severalabs.ias.controller;

import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.UserRequest;
import com.severalabs.ias.service.UserService;
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

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> createUser (@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(
                userService.createUser(userRequest), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin (@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(
                userService.loginUser(userRequest), HttpStatusCode.valueOf(200));
    }
}
