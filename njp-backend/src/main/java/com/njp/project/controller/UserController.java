package com.njp.project.controller;

import com.njp.project.entity.*;
import com.njp.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> save(@Valid @RequestBody User user){
//        System.out.println("Save: " + service.findByUsername(user.getUsername()));
        if(service.findByUsername(user.getUsername())!=null)
            return new ResponseEntity<>(user, HttpStatus.I_AM_A_TEAPOT);

        return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/update/{userId}")
    public ResponseEntity<User> update( @PathVariable Long userId, @Valid @RequestBody User user){
        if(service.findById(userId)==null)
            return null;
        return new ResponseEntity<>(service.update(userId, user), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteById(@PathVariable Long userId){
        service.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/{userId}")
    public ResponseEntity<User> findById( @PathVariable Long userId){
        return new ResponseEntity<>(service.findById(userId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<User> findByUsername( @PathVariable String username){
        return new ResponseEntity<>(service.findByUsername(username), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<User> findAll(){     //(@RequestHeader("Authorization") String authorization){
        return service.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getTypes(){
        return service.getTypes();
    }

//    hasAnyRole([role1,role2])
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/getNumberOfBookings/{username}")
    public int getNumberOfBookings( @PathVariable String username){
        return service.getNumberOfBookings(username);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/getBookingsFromUser/{username}")
    public List<Booking> getBookingsFromUser( @PathVariable String username){
        return service.getBookingsFromUser(username);
    }

}
