package com.abde.tp3.services;

import com.abde.tp3.dtos.UserDTO;
import com.abde.tp3.dtos.UserRegistrationDTO;
import com.abde.tp3.dtos.UserUpdateDTO;
import com.abde.tp3.model.User;
import com.abde.tp3.repos.UserRepo;
import com.abde.tp3.requests.LoginRequestDTO;

public interface UserService {
  // User registerUser(User user);

  String loginUser(LoginRequestDTO loginRequest);

  UserDTO getCurrentUser();

  UserDTO updateUser(String username, UserUpdateDTO user);

  void deleteUser(String username);

  UserDTO convertToDTO(User user);

  UserDTO register(UserRegistrationDTO user);
}
