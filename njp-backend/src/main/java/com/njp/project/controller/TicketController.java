package com.njp.project.controller;



import com.njp.project.entity.Ticket;
import com.njp.project.service.TicketService;
import com.njp.project.util.TicketFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ticket")
@CrossOrigin
public class TicketController {

    @Autowired
    private final TicketService service;

    public TicketController(TicketService service){
        this.service = service;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> save(@Valid @RequestBody Ticket ticket){
        Ticket savedTicket;
        savedTicket = service.save(ticket);
        if(savedTicket==null)
            return new ResponseEntity<>(ticket, HttpStatus.CONFLICT);

        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/update/{ticketId}")
    public ResponseEntity<Ticket> update(@PathVariable Long ticketId, @Valid @RequestBody Ticket ticket){
        if(service.findById(ticketId)==null)
            return new ResponseEntity<>(ticket, HttpStatus.I_AM_A_TEAPOT);
        Ticket updatedTicket;
        updatedTicket = service.update(ticketId, ticket);
        if(updatedTicket==null)
            return new ResponseEntity<>(ticket, HttpStatus.CONFLICT);

        return new ResponseEntity<>(updatedTicket, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<?> deleteById(@PathVariable Long ticketId){
        service.deleteById(ticketId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/{ticketId}")
    public ResponseEntity<Ticket> findById( @PathVariable Long ticketId){
        return new ResponseEntity<>(service.findById(ticketId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/all")
    public List<Ticket> findAll(){     //(@RequestHeader("Authorization") String authorization){
        return service.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getTypes(){
        return service.getTypes();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping(value = "/reserve/{numberOfTickets}")//, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> save(@Valid @RequestBody Ticket ticket, @PathVariable Long numberOfTickets){
        if(ticket.getNumberOfAvailableTickets()<numberOfTickets)
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(service.reserve(ticket, numberOfTickets), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value = "/paginated")
    public ResponseEntity<List<Ticket>> findAllPaginated(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                         @RequestParam(defaultValue = "0") Integer way){
        List<Ticket> list = new ArrayList<>();
        if(way == 0)
            list = service.findAllPaginated(pageNo, pageSize);//, sortBy);
        if(way == 1)
            list = service.findAllPaginatedBasedOnWay(pageNo, pageSize,true);//, sortBy);
        if(way == 2)
            list = service.findAllPaginatedBasedOnWay(pageNo, pageSize,false);//, sortBy);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @PostMapping(value = "/filter")
    public List<Ticket> filterTickets(@RequestBody TicketFilter ticketFilter) {
        return service.filterTickets(ticketFilter);
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
