package com.example.allAuth.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private  String username;
    private String email;
    private String phoneNumber;
    private Set<String> roles;
    private String password;

    private String resetToken;
    private LocalDateTime tokenExpirationDate;
}
