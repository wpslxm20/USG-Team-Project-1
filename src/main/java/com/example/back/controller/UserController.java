package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.dto.UserDTO;
import com.example.back.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service")
public class UserController {
    
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerpage(@RequestBody UserDTO dto){
        userService.registerService(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginpage(@RequestBody UserDTO dto){
        userService.loginService(dto);
        return ResponseEntity.ok().body(dto);
    }
}
