package com.example.carvajaltechnicaltest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String userName;
    private String password;
    private String email;
    private String[] dna;
    private RoleDto roleId;
}
