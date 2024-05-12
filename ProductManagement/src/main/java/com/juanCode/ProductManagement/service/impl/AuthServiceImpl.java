package com.juanCode.ProductManagement.service.impl;

import com.juanCode.ProductManagement.controller.dtos.AuthResponse;
import com.juanCode.ProductManagement.controller.dtos.LoginRequest;
import com.juanCode.ProductManagement.controller.dtos.SigInRequest;
import com.juanCode.ProductManagement.entity.UserEntity;
import com.juanCode.ProductManagement.repository.UserRepository;
import com.juanCode.ProductManagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService = new JwtService();
    @Autowired
    private PasswordEncoder passwordEncoder ;
    @Autowired
     private AuthenticationManager authenticationManager;



  public AuthResponse login(LoginRequest loginRequest) {

      String username = loginRequest.getUsername();
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

      UserDetails user = userRepository.findByUsername(username)
              .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

      String token = jwtService.getToken(user);
      return AuthResponse.builder()
              .token(token)
              .build();
      }

  public AuthResponse sigin(SigInRequest sigInRequest) {

    UserEntity user =
        UserEntity.builder()
            .id(sigInRequest.getId())
            .username(sigInRequest.getUsername())
            .firstname(sigInRequest.getFirstname())
            .lastname(sigInRequest.getLastname())
            .email(sigInRequest.getEmail())
            .password(passwordEncoder.encode(sigInRequest.getPassword()))
            .build();
    userRepository.save(user);
    return AuthResponse.builder().token(jwtService.getToken(user)).build();
        }
}
