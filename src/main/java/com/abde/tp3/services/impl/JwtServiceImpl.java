package com.abde.tp3.services.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.abde.tp3.model.UserPrincipale;
import com.abde.tp3.repos.UserRepo;
import com.abde.tp3.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@Slf4j
@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {
  private final String SECRET_KEY = "b8d13585bc354d8e7246544a62670fd766f96f57ca034cc3dc11d3ef8c432d9a1ec090f9c4d9f2437a8ae63414254d794e1bc617c446fdfa0feb9097bf84fd164df2331d53f07e2cdb4e12ba9a385a0a00f32a5ec5eb43431287616c904e78dabd55a87bf6a635d7bbb409887f86cb48625341c7a8fbfcd8f3530a3728449109b318787374bc3622c82db69308e55e58dbd49297ba18452fc636592923873e8b6f5fea9b27507575ecc5c04d42e187f2b7963f64605edd8b6d734e9ff036f961faec90a24938d0e137180a3fd9730088886b553cddc5e411425837e84549d7e6cff4645da25c0d99913644b35f899d8d0fedb14f7657403e3993cfb4e0d1697638c5d891aa05293a86cfeb148284021a1aa15e96b9683508ec6286f38cf7f3c47d54466b8970a065d6b80a98398fe28820d29dc104632757192e9533f92df343f961db66fdfe523909afa8c3120755aefa38bb928b2726c5b12fab3a6fb9401a130af9a9dd556f5a5e659bb47770c943130ef7f6853c8676f92666887d985772d292c9a2f570e101ac6318752c9aecfd9a74c01211bfee3bce6ec54341023ee82a3947e0662691341cbd1eb27056783a8136f5cb682579daa5c58cb21972903d36f526c24068f51133cf3d66f15ac5a6ee04664a7effb165a16198a890c56e7fb3a6bee2d4ae79c395beb2c8afb36130ddc96570d010baf77ff2a82ac8e175f5";
  private final UserRepo userRepo;

  @Override
  public String generateToken(String username) {
    UserDetails userDetails = new UserPrincipale(userRepo.findUserByEmail(username));
    return generateToken(userDetails);
  }

  @Override
  public String generateToken(String username, Map<String, Object> extraClaims) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("allowed_types", new String[] { "pdf", "docx", "jpeg", "jpg" });
    return Jwts.builder()
        .claims()
        .add(claims)
        .add(extraClaims)
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
        .and()
        .signWith(getKey())
        .compact();
  }

  @Override
  public boolean validateToken(String token, UserDetails userDetails) {
    log.info(("token username: "+ extractUsername(token)));
    log.info("is token expired: "+ isTokenExpired(token));
    return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("allowed_types", new String[] { "pdf", "docx", "jpeg", "jpg" });
    return Jwts.builder()
        .claims()
        .add(claims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
        .and()
        .signWith(getKey())
        .compact();

  }

  private Key getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  @Override
  public boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public <T> T extractClaim(String token, Function<Claims, T> resolver) {
    Claims claims = extractAllClaims(token);
    return resolver.apply(claims);
  }

  @Override
  public Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith((SecretKey) getKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  @Override
  public String getSigningKey() {
    return getKey().toString();
  }

  @Override
  public Date getTokenExpiration(String token) {
    return extractClaim(token, Claims::getIssuedAt);
  }

}
