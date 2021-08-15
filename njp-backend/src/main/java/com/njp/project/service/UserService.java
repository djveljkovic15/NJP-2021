package com.njp.project.service;


import com.njp.project.entity.*;

import java.util.List;

public interface UserService {
    User save(User user);

    User update(Long id, User user);

    void deleteById(Long id);

    User findById(Long id);

    User findByUsername(String username);

    List<User> findAll();

    List<String> getTypes();

    int getNumberOfBookings(String username);

    List<Booking> getBookingsFromUser(String username);
}
