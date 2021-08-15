package com.njp.project.bootstrap;

import com.njp.project.entity.*;
import com.njp.project.model.TicketType;
import com.njp.project.model.UserType;
import com.njp.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BootstrapData implements CommandLineRunner {


    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading data...");



        City city1 = new City();
        City city2 = new City();
        City city3 = new City();
        city1.setName("City1");
        city2.setName("City2");
        city3.setName("City3");

        city1 = cityRepository.save(city1);
        city2 = cityRepository.save(city2);
        city3 = cityRepository.save(city3);

        Company company1 = new Company();
        company1.setName("Company1");
        company1 = companyRepository.save(company1);

        Company company2 = new Company();
        company2.setName("Company2");
        company2 = companyRepository.save(company2);


        Flight flight1 = new Flight();
        flight1.setOrigin(city1);
        flight1.setDestination(city2);
        List<Ticket> flight1Tickets = new ArrayList<>();
        flight1 = flightRepository.save(flight1);
//        Ticket ticket1 = ticketRepository.save(new Ticket(null, company1, TicketType.BUSINESS_CLASS, true, new Date(), new Date(), flight1, 1L));
//        flight1Tickets.add(ticket1);
        flight1Tickets.add(ticketRepository.save(new Ticket(null, company1, TicketType.BUSINESS_CLASS, true, new Date(), new Date(), flight1, 1L)));
        flight1Tickets.add(ticketRepository.save(new Ticket(null, company1, TicketType.FIRST_CLASS, true, new Date(), new Date(), flight1, 2L)));
        flight1Tickets.add(ticketRepository.save(new Ticket(null, company1, TicketType.BUSINESS_CLASS, false, new Date(), new Date(), flight1, 3L)));
        flight1Tickets.add(ticketRepository.save(new Ticket(null, company1, TicketType.FIRST_CLASS, false, new Date(), new Date(), flight1, 4L)));
        flight1.setTickets(flight1Tickets);


        Flight flight2 = new Flight();
        flight2.setOrigin(city2);
        flight2.setDestination(city3);
        List<Ticket> flight2Tickets = new ArrayList<>();
        flight2 = flightRepository.save(flight2);
        Date tomorrow = new Date(System.currentTimeMillis()+24*60*60*1000);
        Date dayAfterTomorrow = new Date(System.currentTimeMillis()+2*24*60*60*1000);
        Date tenDaysAfterTomorrow = new Date(System.currentTimeMillis()+10*24*60*60*1000);
        flight2Tickets.add(ticketRepository.save(new Ticket(null, company2, TicketType.BUSINESS_CLASS, true, tomorrow, tomorrow, flight2, 5L)));
        flight2Tickets.add(ticketRepository.save(new Ticket(null, company2, TicketType.FIRST_CLASS, true, tenDaysAfterTomorrow, tenDaysAfterTomorrow, flight2, 6L)));
        flight2Tickets.add(ticketRepository.save(new Ticket(null, company2, TicketType.BUSINESS_CLASS, false, dayAfterTomorrow, tenDaysAfterTomorrow, flight2, 7L)));
        flight2Tickets.add(ticketRepository.save(new Ticket(null, company2, TicketType.FIRST_CLASS, false, new Date(), new Date(), flight2, 8L)));
        flight2.setTickets(flight2Tickets);

        flight1 = flightRepository.save(flight1);
        flight2 = flightRepository.save(flight2);

        List<Booking> bookings1 = new ArrayList<>();
        bookings1.add(bookingRepository.save(new Booking(null, true, flight1, flight1Tickets.get(0))));
        List<Booking> bookings2 = new ArrayList<>();
        bookings2.add(bookingRepository.save(new Booking(null, true, flight1, flight1Tickets.get(0))));
        bookings2.add(bookingRepository.save(new Booking(null, true, flight1, flight1Tickets.get(1))));
        bookings2.add(bookingRepository.save(new Booking(null, true, flight2, flight2Tickets.get(1))));
        bookings2.add(bookingRepository.save(new Booking(null, true, flight2, flight2Tickets.get(2))));
        bookings2.add(bookingRepository.save(new Booking(null, true, flight2, flight2Tickets.get(3))));

        User user1 = new User();
        user1.setUsername("dzo");
        user1.setPassword(passwordEncoder.encode("123"));
        user1.setUserType(UserType.ADMIN);
        user1.setBookings(bookings1);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("ale");
        user2.setPassword(passwordEncoder.encode("123"));
        user2.setUserType(UserType.USER);
        user2.setBookings(bookings2);
        userRepository.save(user2);


    }
}
