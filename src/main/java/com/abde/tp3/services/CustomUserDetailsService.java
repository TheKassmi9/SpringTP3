package com.abde.tp3.services;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abde.tp3.model.User;
import com.abde.tp3.model.UserPrincipale;
import com.abde.tp3.repos.UserRepo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Slf4j
@Data
@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
  final private UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findUserByEmail(username);
    log.info("User Email: "+user.getEmail());
    if (user == null) {
      throw new UsernameNotFoundException(username + ": This Email Doesn't Exist");
    }
    UserDetails userDetails = new UserPrincipale(user);
    return userDetails;
  }

}
