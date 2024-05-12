package com.juanCode.ProductManagement.controller.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SigInRequest {

    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;

}
