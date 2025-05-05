package com.abde.tp3.services;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtService {
  String generateToken(String username);

  String generateToken(String username, Map<String, Object> extraClaims);

  boolean validateToken(String token, UserDetails userDetails);

  String generateToken(UserDetails userDetails);

  boolean isTokenExpired(String token);

  String extractUsername(String token);

  <T> Object extractClaim(String token, Function<Claims, T> resolver);

  Claims extractAllClaims(String token);

  String getSigningKey();

  Date getTokenExpiration(String token);
}
