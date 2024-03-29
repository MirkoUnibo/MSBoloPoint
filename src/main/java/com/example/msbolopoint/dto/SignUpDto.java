package com.example.msbolopoint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;
}
