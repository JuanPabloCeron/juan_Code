package com.jpceron.CarRegistry.controller;

import com.jpceron.CarRegistry.controller.dtos.LoginRequest;
import com.jpceron.CarRegistry.controller.dtos.SignUpRequest;
import com.jpceron.CarRegistry.service.UserService;
import com.jpceron.CarRegistry.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final AuthenticationServiceImpl authenticationServiceImpl;

   @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request){
        try{

            return ResponseEntity.ok(authenticationServiceImpl.signUp(request));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        return ResponseEntity.ok(authenticationServiceImpl.login(request));
    }


    @PostMapping(value = "/userImage/{id}/add")
    public ResponseEntity<String> addImage(@PathVariable Long id, @RequestParam("imageFile") MultipartFile imageFile){
        try{

            userService.addUserImage(id,imageFile);
            return ResponseEntity.ok("Image succesfully saved.");

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
    }

    @GetMapping(value = "/userImage/{id}")
    public ResponseEntity <byte[]> getImage(@PathVariable Long id){
        try{

            byte[] imageBytes = userService.getUserImage(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageBytes, headers,HttpStatus.OK);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
