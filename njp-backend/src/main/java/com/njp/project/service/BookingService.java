package com.njp.project.service;


import com.njp.project.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking save(Booking booking);

    Booking update(Long id, Booking booking);

    void deleteById(Long id);

    Booking findById(Long id);

    List<Booking> findAll();

    boolean cancelBooking(String username, Long bookingId);

    boolean buyBooking(String username, Long bookingId);

    List<Booking> getBookingsByUsername(String username);
}
