package com.example.allAuth.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter

public class JwtResponse {
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private int id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, String refreshToken, int id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }


}
