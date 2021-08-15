package com.njp.project.service.impl;


import com.njp.project.entity.*;
import com.njp.project.repository.*;
import com.njp.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private final BookingRepository repository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TicketRepository ticketRepository;

    public BookingServiceImpl(BookingRepository repository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Booking save(Booking booking) {
        return repository.save(booking);

    }
    @Override
    public Booking update(Long id, Booking booking) {
        if(findById(id)==null)
            return null;
        return repository.save(booking);

    }

    @Override
    public void deleteById(Long id) {
        Booking booking = findById(id);
        if(booking.getTicket().getDepartDate().after(new Date())) {
            deleteBookingFromUserBookings(booking);
            repository.deleteById(id);
        }
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
    public Booking findById(Long id) {
        Booking booking = repository.findById(id).orElse(null);
//        if(booking!=null && booking.getIsAvailable() && !checkIfAvailable(booking)){
        if(booking!=null && !checkIfAvailable(booking)){
            booking.setIsAvailable(false);
            repository.save(booking);
        }
        return repository.findById(id).orElse(null);
    }


    @Override
    public List<Booking> findAll() {
        // Prodjem kroz sve koje sam nasao i ako im je datum<danas kazem gotova?
        List<Booking> bookings = repository.findAll();
        for(Booking booking:bookings){
//            if(booking.getIsAvailable()&&!checkIfAvailable(booking)){
            if(!checkIfAvailable(booking)){
                booking.setIsAvailable(false);
                repository.save(booking);
            }
        }
        return repository.findAll();
    }
    public boolean checkIfAvailable(Booking booking){
        Date now = new Date();
        System.out.println("Now: " + now);
        System.out.println("Depart date: " + booking.getTicket().getDepartDate());
        if(!booking.getTicket().getDepartDate().before(now)) {
            return true;
        }

        return false;
    }

    @Override
    public synchronized boolean cancelBooking(String username, Long bookingId){

        if(!checkIfCancelable(bookingId))
            return false;

        User user = userRepository.findByUsername(username);
//        System.out.println(booking);
//        List<Booking> bookings = user.getBookings();
//        bookings.remove(booking);
//        user.setBookings(bookings);
////        repository.delete(booking);
//        userRepository.save(user);
        removeBookingFromUser(user,bookingId, "cancel");
//        Ticket ticket = booking.getTicket();
//        Ticket ticket2 = ticketRepository.findById(booking.getTicket().getId());
        //ticket.setNumberOfAvailableTickets(ticket.getNumberOfAvailableTickets()+1);
//        ticketRepository.save(ticket);

        return true;
    }

    private void removeBookingFromUser(User user, Long bookingId, String type){
        List<Booking> bookings = user.getBookings();
        Booking booking = findById(bookingId);
        for (Booking booking1:bookings){
            if (booking.equals(booking1))
                System.out.println("Same ID: " + booking1.getId());
        }
        System.out.println("0: " + booking);
        System.out.println("1: " + bookings);
        bookings.remove(booking);
        System.out.println("2: " + bookings);

        user.setBookings(bookings);
        userRepository.save(user);

        if(type.matches("cancel")) {
            Ticket ticket = booking.getTicket();
            ticket.incrementNumberOfAvailableTickets();
            ticketRepository.save(ticket);
        }
        if(type.matches("buy")) {
            // Kod nas nista jer je vec dekrementovan.
            // Da postoji neki flag koji bi obelezavao kupljene itd ovde bih postavio.
        }
    }

    public boolean buyBooking(String username, Long bookingId){

        User user = userRepository.findByUsername(username);
        removeBookingFromUser(user,bookingId, "buy");

        return true;
    }

    private boolean checkIfCancelable(Long bookingId){
        Booking booking = findById(bookingId);
        if(!(booking.getTicket().getDepartDate().before(getMeTomorrow())))
            return true;
        return false;
    }
    private Date getMeTomorrow(){
        return new Date(System.currentTimeMillis()+24*60*60*1000);
    }

    @Override
    public List<Booking> getBookingsByUsername(String username){

        User user = userRepository.findByUsername(username);

        List<Booking> userBookings = user.getBookings();

        for(Booking userBooking: userBookings){
//            if(userBooking.getIsAvailable()&&!checkIfAvailable(userBooking)){
            if(!checkIfAvailable(userBooking)){
                userBooking.setIsAvailable(false);

                repository.save(userBooking);
            }
        }

        List<Booking> finalBookings = new ArrayList<>();
        List<Booking> bookings = repository.findAll();
        for (Booking booking: bookings){
            for(Booking bookingUser: userBookings){
                if(booking.getId().equals(bookingUser.getId())){
                    finalBookings.add(booking);
                }
            }
        }

        return finalBookings;
    }

}
