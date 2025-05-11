package com.abde.tp3.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abde.tp3.Enums.Roles;
import com.abde.tp3.dtos.UserDTO;
import com.abde.tp3.dtos.UserRegistrationDTO;
import com.abde.tp3.dtos.UserUpdateDTO;
import com.abde.tp3.model.User;
import com.abde.tp3.repos.UserRepo;
import com.abde.tp3.requests.LoginRequestDTO;
import com.abde.tp3.services.JwtService;
import com.abde.tp3.services.UserService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  @Autowired
  AuthenticationManager authManager;
  @Autowired
  private final UserRepo userRepo;
  @Autowired
  private final JwtService jwtService;

  @Override
  public String loginUser(LoginRequestDTO loginRequest) {
    System.out.println("Before fetching form the Database");
    User user = userRepo.findUserByEmail(loginRequest.email());
    System.out.println("Login User Data: " + user.getEmail());
    // if (user.getPassword().equals(new
    // BCryptPasswordEncoder(12).encode(loginRequest.password()))) {
    // return jwtService.generateToken(user.getEmail());
    // }
    Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginRequest.email(), loginRequest.password()));
    System.out.println("is User Authenticated: " + authentication.isAuthenticated());
    return authentication.isAuthenticated() ? jwtService.generateToken(loginRequest.email()) : "Failed";
  }

  @Override
  public UserDTO getCurrentUser() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userRepo.findUserByEmail(userDetails.getUsername());
    return convertToDTO(user);
  }

  @Override
  public UserDTO updateUser(String username, UserUpdateDTO user) {
    User userDB = userRepo.findUserByEmail(username);
    if(user.getPassword().equals(userDB.getPassword()) && user.getNewPassword().equals(user.getConfirmPassword())) {
      userDB.setPassword(new BCryptPasswordEncoder(12).encode(user.getNewPassword()));
      userDB.setUsername(user.getUsername());
      userRepo.save(userDB);
    }
    else {
      return null;
    }
    return convertToDTO(userDB);
  }

  @Override
  public void deleteUser(String username) {
    User user = userRepo.findUserByEmail(username);
    userRepo.delete(user);
  }

  @Override
  public UserDTO convertToDTO(User user) {
    return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRoles(), user.getCreatedAt(),
        user.getDocumentCount());
  }

  @Override
  public UserDTO register(UserRegistrationDTO user) {
    User userDB = new User();
    userDB.setEmail(user.getEmail());
    System.out.println("Password: " + user.getPassword());
    userDB.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
    List<Roles> roles = new ArrayList<>();
    roles.add(Roles.USER);
    roles.add(Roles.DOCUMENT_UPLOADER);
    userDB.setRoles(roles);
    userDB.setUsername(user.getUsername());
    userRepo.save(userDB);
    return convertToDTO(userDB);
  }

}
