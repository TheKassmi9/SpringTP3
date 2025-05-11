package com.abde.tp3.model;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Slf4j
public class UserPrincipale implements UserDetails {
  final private User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
//    return this.user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole()))
//        .collect(Collectors.toList());
    log.info("getAuthorities: ");
    return this.user.getRoles().stream().map(role ->new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public String getUsername() {
    return this.user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
