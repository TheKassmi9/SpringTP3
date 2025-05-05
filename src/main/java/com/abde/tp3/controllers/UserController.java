package com.abde.tp3.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abde.tp3.dtos.UserDTO;
import com.abde.tp3.dtos.UserRegistrationDTO;
import com.abde.tp3.requests.LoginRequestDTO;
import com.abde.tp3.services.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class UserController {
  private final UserService userService;

  UserController(UserService userService) {
    this.userService = userService;
  }

  // @PostMapping("/register")
  @PostMapping("/register")
  ResponseEntity<UserDTO> register(@RequestBody UserRegistrationDTO user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(user));
  }

  @PostMapping("/login")
  ResponseEntity<String> login(@RequestBody LoginRequestDTO login) {
    return ResponseEntity.ok().body(userService.loginUser(login));
  }

}
