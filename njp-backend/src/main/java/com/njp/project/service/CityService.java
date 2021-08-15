package com.njp.project.service;


import com.njp.project.entity.City;

import java.util.List;

public interface CityService {
    City save(City city);

    City update(Long id, City city);

    void deleteById(Long id);

    City findById(Long id);

    List<City> findAll();

}
