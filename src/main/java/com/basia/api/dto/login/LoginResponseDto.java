package com.basia.api.dto.login;

import com.basia.api.dto.register.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    String username;
    String token;
    String firstName;
    String lastName;
    String email;
    Roles[] roles;

}
