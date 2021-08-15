package com.njp.project.service.impl;


import com.njp.project.entity.*;
import com.njp.project.repository.*;
import com.njp.project.service.*;
import com.njp.project.util.TicketFilter;
import com.njp.project.util.UserAdminUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private final TicketRepository repository;
    @Autowired
    private final FlightRepository flightRepository;
    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final UserRepository userRepository;

    public TicketServiceImpl(TicketRepository repository, FlightRepository flightRepository, BookingRepository bookingRepository, UserRepository userRepository) {
        this.repository = repository;
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        if(!isTicketFalid(ticket))
            return null;
        Flight flight = ticket.getFlight();

        if(flight.getTickets()==null)
            flight.setTickets(new ArrayList<>());
        System.out.println(flight.getTickets());

        List<Ticket> tickets = flight.getTickets();
        tickets.add(ticket);
        flight.setTickets(tickets);
        ticket.setFlight(flight);

        repository.save(ticket);
        flightRepository.save(flight);
        return repository.save(ticket);
    }
    @Override
    public Ticket update(Long id, Ticket ticket) {
        if(!isTicketFalid(ticket))
            return null;
        if(findById(id)==null)
            return null;
        return repository.save(ticket);
    }
    private boolean isTicketFalid(Ticket ticket) {
        if(ticket.getOneWay() && ticket.getDepartDate().after(ticket.getReturnDate()))
            return false;
        if(ticket.getFlight().getOrigin().equals(ticket.getFlight().getDestination()))
            return false;
        return true;
    }

    @Override
    public void deleteById(Long id) {
        Ticket ticket = repository.findById(id).orElse(null);

        // Ciscenje  Booking
        List<Booking> bookings = bookingRepository.findAll();
        for (Booking booking:bookings)
            if(booking.getTicket().getId().equals(id)){
                deleteBookingFromUserBookings(booking);
                bookingRepository.deleteById(booking.getId());
            }
        // Ciscenje  Flight
        Flight flight = flightRepository.findById(ticket.getFlight().getId()).get();
        List<Ticket> tickets = flight.getTickets();
        tickets.remove(ticket);
        flight.setTickets(tickets);

        flightRepository.save(flight);
        repository.deleteById(id);
    }

    private void deleteBookingFromUserBookings(Booking booking) {
        List<User> users = userRepository.findAll();
        List<Booking> bookings;
        for(User user:users){
            if(user.getBookings().contains(booking)){
                bookings = user.getBookings();
                bookings.remove(booking);
                user.setBookings(bookings);
                userRepository.save(user);
            }
        }
    }

    @Override
    public Ticket findById(Long id) {
           return repository.findById(id).orElse(null);
    }

    @Override
    public List<Ticket> findAll() {
        return repository.findAll();
    }

    @Override
    public List<String> getTypes(){
        return repository.getTypes();
    }

    @Override
    public Ticket reserve(Ticket ticket, Long numberOfTickets) {
        ticket.setNumberOfAvailableTickets(ticket.getNumberOfAvailableTickets()-numberOfTickets);
        System.out.println("Tickets left: " + ticket.getNumberOfAvailableTickets());

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Tikets reserved by: " + userDetails.getUsername());

        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Booking> bookings = user.getBookings();
        for(int i = 0; i<numberOfTickets; i++){
            Booking booking = new Booking();
            booking.setTicket(ticket);
            booking.setFlight(ticket.getFlight());
            booking.setIsAvailable(checkIfAvailable(booking));
            bookings.add(bookingRepository.save(booking));
        }
        user.setBookings(bookings);
        userRepository.save(user);

        return repository.save(ticket);
    }
    public boolean checkIfAvailable(Booking booking){
        if(!booking.getTicket().getDepartDate().before(new Date()))
            return false;
        return true;
    }
    @Override
    public List<Ticket> findAllPaginated(Integer pageNo, Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);//, Sort.by(sortBy));

        Page<Ticket> pagedResult = repository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Ticket>();
        }
    }

    @Override
    public List<Ticket> findAllPaginatedBasedOnWay(Integer pageNo, Integer pageSize, boolean oneWay) {
        Pageable paging = PageRequest.of(pageNo, pageSize);//, Sort.by(sortBy));

        Page<Ticket> pagedResult = repository.findAllByOneWay(paging, oneWay);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Ticket>();
        }
    }

    @Override
    public List<Ticket> filterTickets(TicketFilter ticketFilter) {
        return repository.findBookingsByFilters(ticketFilter.getOriginName(), ticketFilter.getDestinationName(),
                                                ticketFilter.getDepartDate(), ticketFilter.getReturnDate());
    }


}
