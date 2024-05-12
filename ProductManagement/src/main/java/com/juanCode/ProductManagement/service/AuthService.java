package com.juanCode.ProductManagement.service;


import com.juanCode.ProductManagement.controller.dtos.AuthResponse;
import com.juanCode.ProductManagement.controller.dtos.LoginRequest;
import com.juanCode.ProductManagement.controller.dtos.SigInRequest;

public interface AuthService {
    AuthResponse sigin(SigInRequest sigInRequest);
    AuthResponse login (LoginRequest loginRequest);

}
