package com.njp.project.model.authentication;

import com.njp.project.model.UserType;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private final String jwt;
    private UserType userType;


    public AuthenticationResponse(String jwt, UserType userType){
        this.jwt = jwt;
        this.userType = userType;
    }
}
