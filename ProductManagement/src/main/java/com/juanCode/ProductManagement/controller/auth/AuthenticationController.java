package com.juanCode.ProductManagement.controller.auth;

import com.juanCode.ProductManagement.controller.dtos.AuthResponse;
import com.juanCode.ProductManagement.controller.dtos.LoginRequest;
import com.juanCode.ProductManagement.controller.dtos.SigInRequest;
import com.juanCode.ProductManagement.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){

    return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/sigin")
    public  ResponseEntity<AuthResponse> sigin(@RequestBody SigInRequest sigInRequest){

        return ResponseEntity.ok(authService.sigin(sigInRequest));
    }

}
