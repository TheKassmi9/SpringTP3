package com.abde.tp3.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.abde.tp3.services.CustomUserDetailsService;
import com.abde.tp3.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final ApplicationContext context;

  JwtAuthenticationFilter(JwtService jwtService, ApplicationContext context) {
    this.jwtService = jwtService;
    this.context = context;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
//    log.info("In doFilterInternal: "+ SecurityContextHolder.getContext().getAuthentication().toString());
//    System.out.println("the user has not been authenticated yet");
//    System.out.println("JWT FILTER: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    String requestURI = request.getRequestURI();
    if(requestURI.startsWith("/api/auth/")) {
      System.out.println("requestURI: " + requestURI);
      filterChain.doFilter(request, response);
      return;
    }
    SecurityContextHolder.clearContext();
    String authorizationHeader = request.getHeader("Authorization");
    String token = null;
    String username = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      token = authorizationHeader.substring(7);
      username = jwtService.extractUsername(token);
      if(username != null) {
        log.info("username found: " + username);
      }
    }
    else {
      filterChain.doFilter(request, response);
      return;
    }
    UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(username);
    log.info("userdetails.username "+ userDetails.getUsername());
    log.info(("username: "+ username));
    if (username != null) {
      log.info("Is Token Valid: "+ jwtService.validateToken(token, userDetails));
      if (jwtService.validateToken(token, userDetails)) {
        log.info("token validated");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Authentication Success: "+ authentication.isAuthenticated());
        filterChain.doFilter(request, response);
      }
    } else
      filterChain.doFilter(request, response);
  }

}
