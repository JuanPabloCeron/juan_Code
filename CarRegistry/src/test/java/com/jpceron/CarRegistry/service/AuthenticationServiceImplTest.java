package com.jpceron.CarRegistry.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jpceron.CarRegistry.controller.dtos.JwtResponse;
import com.jpceron.CarRegistry.controller.dtos.LoginRequest;
import com.jpceron.CarRegistry.entity.UserEntity;
import com.jpceron.CarRegistry.repository.UserRepository;
import com.jpceron.CarRegistry.service.impl.AuthenticationServiceImpl;
import com.jpceron.CarRegistry.service.impl.JwtService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtService jwtService;

    @Mock
    AuthenticationManager authenticationManager;

    String password = "12345";
    String email = "juan@gmail.com";
    String token = "token";
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    LoginRequest loginRequest = new LoginRequest();
    UserEntity user = new UserEntity();

    @Test
    void test_login(){

        loginRequest.setMail(email);
        loginRequest.setPassword(password);
        usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(loginRequest.getMail(),loginRequest.getPassword());
        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(null);
        when(userRepository.findByEmail(loginRequest.getMail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(token);

        JwtResponse result = authenticationService.login(loginRequest);

        verify(authenticationManager).authenticate(usernamePasswordAuthenticationToken);
        verify(userRepository).findByEmail(loginRequest.getMail());
        verify(jwtService).generateToken(user);

        assertEquals(result.getToken(),token);
    }


}
