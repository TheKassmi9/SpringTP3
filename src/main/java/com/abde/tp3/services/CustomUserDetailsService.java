package com.abde.tp3.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abde.tp3.model.UserPrincipale;
import com.abde.tp3.repos.UserRepo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
  final private UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (userRepo.findByEmail(username) == null) {
      throw new UsernameNotFoundException(userRepo.findByEmail(username).getEmail() + ": This Email Doesn't Exist");
    }
    UserDetails userDetails = new UserPrincipale(userRepo.findByEmail(username));
    return userDetails;
  }

}
