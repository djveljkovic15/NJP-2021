package com.njp.project.service.impl;


import com.njp.project.entity.*;
import com.njp.project.repository.*;
import com.njp.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ALL")
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private final CityRepository repository;

    public CityServiceImpl(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public City save(City city) {
        return repository.save(city);

    }
    @Override
    public City update(Long id, City city) {
        if(findById(id)==null)
            return null;
        return repository.save(city);

    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public City findById(Long id) {
           return repository.findById(id).orElse(null);
    }


    @Override
    public List<City> findAll() {
        return repository.findAll();

    }


}
