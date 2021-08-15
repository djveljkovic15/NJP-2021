package com.njp.project.service.impl;


import com.njp.project.entity.*;
import com.njp.project.repository.*;
import com.njp.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ALL")
@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private final FlightRepository repository;

    public FlightServiceImpl(FlightRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flight save(Flight flight) {
        if(!isFlightFalid(flight))
            return null;
        return repository.save(flight);

    }
    @Override
    public Flight update(Long id, Flight flight) {
        if(!isFlightFalid(flight))
            return null;
        if(findById(id)==null)
            return null;
        return repository.save(flight);

    }
    private boolean isFlightFalid(Flight flight) {
        if(flight.getOrigin().equals(flight.getDestination()))
            return false;
        return true;
    }
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Flight findById(Long id) {
           return repository.findById(id).orElse(null);
    }


    @Override
    public List<Flight> findAll() {
        return repository.findAll();
    }


}
