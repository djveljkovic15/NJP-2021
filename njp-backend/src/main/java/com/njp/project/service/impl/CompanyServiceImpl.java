package com.njp.project.service.impl;


import com.njp.project.entity.*;
import com.njp.project.repository.*;
import com.njp.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ALL")
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private final CompanyRepository repository;

    public CompanyServiceImpl(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Company save(Company company) {
        return repository.save(company);

    }
    @Override
    public Company update(Long id, Company company) {
        if(findById(id)==null)
            return null;
        return repository.save(company);

    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Company findById(Long id) {
           return repository.findById(id).orElse(null);
    }


    @Override
    public List<Company> findAll() {
        return repository.findAll();

    }


}
