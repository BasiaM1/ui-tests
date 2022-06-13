package com.basia.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class LoginResponseDto {

    String username;
    String token;
    String firstName;
    String lastName;
    String email;
    Roles[] roles;

}
