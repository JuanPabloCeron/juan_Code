package com.jpceron.CarRegistry.service.impl;

import com.jpceron.CarRegistry.entity.UserEntity;
import com.jpceron.CarRegistry.repository.UserRepository;
import com.jpceron.CarRegistry.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;


    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserEntity save(UserEntity newUser) {
        return userRepository.save(newUser);
    }


    @Override
    public void addUserImage(Long id, MultipartFile file) throws IOException {
        UserEntity entity = userRepository.findById(id).orElseThrow(RuntimeException::new);

        log.info("Saving user Image ......");
        entity.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        log.info("Image: "+entity.getImage());

        userRepository.save(entity);
    }

    @Override
    public byte[] getUserImage(Long id) {

        UserEntity entity = userRepository.findById(id).orElseThrow(RuntimeException::new);

        return  Base64.getDecoder().decode(entity.getImage());
    }

}
