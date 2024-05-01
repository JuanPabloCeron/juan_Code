package com.jpceron.CarRegistry.service;

import com.jpceron.CarRegistry.controller.dtos.JwtResponse;
import com.jpceron.CarRegistry.controller.dtos.LoginRequest;
import com.jpceron.CarRegistry.controller.dtos.SignUpRequest;
import org.apache.coyote.BadRequestException;

public interface AuthenticationService {

    public JwtResponse signUp (SignUpRequest request)throws BadRequestException;

    public JwtResponse login(LoginRequest request);
}
