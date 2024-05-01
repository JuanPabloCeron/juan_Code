package com.jpceron.CarRegistry.service;

import com.jpceron.CarRegistry.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    UserEntity save(UserEntity newUser);
   // UserDetailsService userDetailsService();

    void addUserImage(Long id, MultipartFile file) throws IOException;

    byte[] getUserImage(Long id );
}
