package com.njp.project.controller;



import com.njp.project.entity.Company;
import com.njp.project.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {

    @Autowired
    private final CompanyService service;

    public CompanyController(CompanyService service){
        this.service = service;
    }


    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> save(@Valid @RequestBody Company company){
        return new ResponseEntity<>(service.save(company), HttpStatus.CREATED);
    }
    @PutMapping("/update/{companyId}")
    public ResponseEntity<Company> update(@PathVariable Long companyId, @Valid @RequestBody Company company){
        if(service.findById(companyId)==null)
            return null;
        return new ResponseEntity<>(service.update(companyId, company), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<?> deleteById(@PathVariable Long companyId){
        service.deleteById(companyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{companyId}")
    public ResponseEntity<Company> findById(@PathVariable Long companyId){
        return new ResponseEntity<>(service.findById(companyId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Company> findAll(){     //(@RequestHeader("Authorization") String authorization){
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
