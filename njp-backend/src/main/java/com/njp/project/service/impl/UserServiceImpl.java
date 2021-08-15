package com.njp.project.service.impl;


import com.njp.project.repository.BookingRepository;
import com.njp.project.util.UserAdminUtil;
import com.njp.project.entity.*;
import com.njp.project.repository.UserRepository;
import com.njp.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository repository;
    @Autowired
    private final BookingRepository bookingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, BookingRepository bookingRepository) {
        this.repository = repository;

        this.bookingRepository = bookingRepository;
    }

    @Override
    public User save(User user) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (UserAdminUtil.isRole_admin(userDetails)){
            String pass = user.getPassword();
            if(pass.length() < 3 || !pass.matches("[A-Za-z0-9 ]+")){
                throw new RuntimeException("Invalid properties");
            }

            String encryptedPass = passwordEncoder.encode(pass);
            user.setPassword(encryptedPass);

            System.out.println("User save: " + repository.findByUsername(user.getUsername()));
            if(repository.findByUsername(user.getUsername())!=null)
                return null;


            return repository.save(user);
        }
        return null;
    }
    @Override
    public User update(Long id, User user) {
        // Proveriti da li je pass plaintext, ako jeste hesiraj ga
        if(findById(id)==null)
            return null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (UserAdminUtil.isRole_admin(userDetails)){
            String pass = user.getPassword();
            System.out.println("New password: " + pass);
            System.out.println("Old password: " + repository.findById(id).get().getPassword());

            if(!pass.equals(repository.findById(id).get().getPassword())) {
                String encryptedPass = passwordEncoder.encode(pass);
                user.setPassword(encryptedPass);
                if(pass.length() < 6 || !pass.matches("[A-Za-z0-9 ]+")){
                    throw new RuntimeException("Password not written correctly!");
                }
            }

            return repository.save(user);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (UserAdminUtil.isRole_admin(userDetails)){
            repository.deleteById(id);
        }
    }

    @Override
    public User findById(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (UserAdminUtil.isRole_admin(userDetails)) {
            return repository.findById(id).orElse(null);
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<String> getTypes(){
        return repository.getTypes();
    }

    public int getNumberOfBookings(String username) {
        return repository.findByUsername(username).getBookings().size();
    }

    public List<Booking> getBookingsFromUser(String username){
//        User user = repository.findByUsername(username);
//        if(user.getBookings()==null)
//            user.setBookings(new ArrayList<>());
//        System.out.println("Bookings: "+user.getBookings());
//        List<Booking> bookings = user.getBookings();
//        if(bookings==null)
//            bookings = new ArrayList<>();
//        for(Booking booking: bookings){
//            if(booking.getIsAvailable()&&!checkIfAvailable(booking)) {
//                booking.setIsAvailable(false);
//                bookingRepository.save(booking);
//            }
//        }
        return repository.findByUsername(username).getBookings();
    }
    public boolean checkIfAvailable(Booking booking){
        if(!booking.getTicket().getDepartDate().before(new Date()))
            return false;
        return true;
    }

}
