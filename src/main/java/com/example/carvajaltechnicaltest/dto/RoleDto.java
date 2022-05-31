package com.example.carvajaltechnicaltest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RoleDto {
   private String name;
   private LocalDate createAt;
}
