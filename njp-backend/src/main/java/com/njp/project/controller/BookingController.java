package com.njp.project.controller;



import com.njp.project.entity.Booking;
import com.njp.project.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class BookingController {

    @Autowired
    private final BookingService service;

    public BookingController(BookingService service){
        this.service = service;
    }


    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> save(@Valid @RequestBody Booking booking){
        return new ResponseEntity<>(service.save(booking), HttpStatus.CREATED);
    }
    @PutMapping("/update/{bookingId}")
    public ResponseEntity<Booking> update(@PathVariable Long bookingId, @Valid @RequestBody Booking booking){
        if(service.findById(bookingId)==null)
            return null;
        return new ResponseEntity<>(service.update(bookingId, booking), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<?> deleteById(@PathVariable Long bookingId){
        service.deleteById(bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{bookingId}")
    public ResponseEntity<Booking> findById(@PathVariable Long bookingId){
        return new ResponseEntity<>(service.findById(bookingId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Booking> findAll(){     //(@RequestHeader("Authorization") String authorization){
        return service.findAll();
    }

    @DeleteMapping("/cancel/{username}/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable String username, @PathVariable Long bookingId){
        boolean canceled  = service.cancelBooking(username, bookingId);
        if(canceled)
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/buy/{username}/{bookingId}")
    public ResponseEntity<?> buyBooking(@PathVariable String username, @PathVariable Long bookingId){
        boolean bought  = service.buyBooking(username, bookingId);
        if(bought)
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }
    @GetMapping("/username/{username}")
    public List<Booking> getBookingsByUsername(@PathVariable String username){     //(@RequestHeader("Authorization") String authorization){

        return service.getBookingsByUsername(username);
    }


    /*
    @PostMapping("/addUserToTeam/{userId}/{teamId}")
    public ResponseEntity<?> addUserToTeam(@PathVariable Long userId, @PathVariable Long teamId){
        if(service.findById(teamId)==null || userService.findById(userId)==null)
            return null;
        service.addUserToTeam(userId,teamId);
        return new ResponseEntity<>(HttpStatus.OK);
        //return new ResponseEntity<>(service.addUserToTeam(userId,teamId),HttpStatus.CREATED);
    }

    @PostMapping("/addProjectToTeam/{projectId}/{teamId}")
    public ResponseEntity<?> addProjectToTeam(@PathVariable Long projectId, @PathVariable Long teamId){
        if(service.findById(teamId)==null || projectService.findById(projectId)==null)
            return null;
        service.addProjectToTeam(projectId, teamId);
        return new ResponseEntity<>(HttpStatus.OK);
        //return new ResponseEntity<>(service.addUserToTeam(userId,teamId),HttpStatus.CREATED);
    }


    @GetMapping("/findTeamByUser/{userId}")
    public ResponseEntity<Team> findTeamByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(service.findTeamByUser(userId), HttpStatus.OK);
    }
    @GetMapping("/findTeamByUser")
    public ResponseEntity<Team> findTeamByUser() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(service.findTeamByUser(myUserDetails.getUser().getId()), HttpStatus.OK);
    }

    */
}
