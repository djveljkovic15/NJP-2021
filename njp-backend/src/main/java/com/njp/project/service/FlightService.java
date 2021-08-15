package com.njp.project.service;


import com.njp.project.entity.Flight;

import java.util.List;

public interface FlightService {
    Flight save(Flight flight);

    Flight update(Long id, Flight flight);

    void deleteById(Long id);

    Flight findById(Long id);

    List<Flight> findAll();

}
