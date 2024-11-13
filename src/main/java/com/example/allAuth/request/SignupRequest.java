package com.example.allAuth.request;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Setter
@Getter
public class SignupRequest {

    private String username;

    private String email;

    private String mobileNumber;

    private Set<String> roles= new HashSet<>();

    private String password;

}
