package com.njp.project.controller;



import com.njp.project.entity.City;
import com.njp.project.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/city")
@CrossOrigin
public class CityController {

    @Autowired
    private final CityService service;

    public CityController(CityService service){
        this.service = service;
    }


    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<City> save(@Valid @RequestBody City city){
        return new ResponseEntity<>(service.save(city), HttpStatus.CREATED);
    }
    @PutMapping("/update/{cityId}")
    public ResponseEntity<City> update(@PathVariable Long cityId, @Valid @RequestBody City city){
        if(service.findById(cityId)==null)
            return null;
        return new ResponseEntity<>(service.update(cityId, city), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{cityId}")
    public ResponseEntity<?> deleteById(@PathVariable Long cityId){
        service.deleteById(cityId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{cityId}")
    public ResponseEntity<City> findById(@PathVariable Long cityId){
        return new ResponseEntity<>(service.findById(cityId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<City> findAll(){     //(@RequestHeader("Authorization") String authorization){
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
