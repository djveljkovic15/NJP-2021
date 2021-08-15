package com.njp.project.controller;



import com.njp.project.entity.Flight;
import com.njp.project.entity.Ticket;
import com.njp.project.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/flight")
@CrossOrigin
public class FlightController {

    @Autowired
    private final FlightService service;

    public FlightController(FlightService service){
        this.service = service;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> save(@Valid @RequestBody Flight flight){
        Flight savedFlight;
        savedFlight = service.save(flight);
        if(savedFlight==null)
            return new ResponseEntity<>(flight, HttpStatus.CONFLICT);
        return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{flightId}")
    public ResponseEntity<Flight> update(@PathVariable Long flightId, @Valid @RequestBody Flight flight){
        if(service.findById(flightId)==null)
            return null;
        Flight updatedFlight;
        updatedFlight = service.update(flightId, flight);
        if(updatedFlight==null)
            return new ResponseEntity<>(flight, HttpStatus.CONFLICT);
        return new ResponseEntity<>(updatedFlight, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{flightId}")
    public ResponseEntity<?> deleteById(@PathVariable Long flightId){
        service.deleteById(flightId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/{flightId}")
    public ResponseEntity<Flight> findById(@PathVariable Long flightId){
        return new ResponseEntity<>(service.findById(flightId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Flight> findAll(){     //(@RequestHeader("Authorization") String authorization){
        return service.findAll();
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
