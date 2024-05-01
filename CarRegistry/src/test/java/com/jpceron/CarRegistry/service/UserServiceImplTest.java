package com.jpceron.CarRegistry.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jpceron.CarRegistry.entity.UserEntity;
import com.jpceron.CarRegistry.repository.UserRepository;
import com.jpceron.CarRegistry.service.impl.UserServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    String userMail="juan@gmail.com";
    UserEntity user = new UserEntity();

    @Test
    void test_userDetailsService(){

        user.setEmail(userMail);
        when(userRepository.findByEmail(userMail)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userMail);

        verify(userRepository).findByEmail(userMail);

        assertEquals(userMail,user.getEmail());

    }

    @Test
    void test_save(){

        when(userRepository.save(user)).thenReturn(user);

        UserEntity resault = userService.save(user);

        verify(userRepository).save(user);

        assertEquals(resault,user);

    }
}
