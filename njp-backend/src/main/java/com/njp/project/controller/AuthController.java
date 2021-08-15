package com.njp.project.controller;


import com.njp.project.model.MyUserDetails;
import com.njp.project.model.UserType;
import com.njp.project.model.authentication.AuthenticationRequest;
import com.njp.project.model.authentication.AuthenticationResponse;
import com.njp.project.service.impl.MyUserDetailsService;
import com.njp.project.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {

        try {
            System.out.println(authenticationRequest.getUsername() + " " + authenticationRequest.getPassword());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e){
            e.printStackTrace();
            ResponseEntity.status(401).build(); // Nepotrebno kolko se secam?
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        final MyUserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        UserType type = userDetails.getUser().getUserType();
        final String jwt = jwtUtil.generateToken(userDetails);

/*
        String userType = "";
        if(type.equals(UserType.ADMIN)){
            userType = "ADMIN";

        } else {
            userType = "USER";

        }
        return new ResponseEntity<>(new AuthenticationResponse(jwt,UserType.valueOf(userType)),HttpStatus.OK);
*/
        return new ResponseEntity<>(new AuthenticationResponse(jwt,type),HttpStatus.OK);

    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<Boolean> tokenCheck(@PathVariable String token){
        final MyUserDetails userDetails;
        try{
            userDetails = myUserDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
        }
        catch (Exception e){
            return new ResponseEntity<>(false,HttpStatus.OK);
        }
        return new ResponseEntity<>(jwtUtil.validateToken(token,userDetails),HttpStatus.OK);

    }

}
