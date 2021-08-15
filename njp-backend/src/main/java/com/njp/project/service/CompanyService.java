package com.njp.project.service;


import com.njp.project.entity.Company;

import java.util.List;

public interface CompanyService {
    Company save(Company company);

    Company update(Long id, Company company);

    void deleteById(Long id);

    Company findById(Long id);

    List<Company> findAll();

}
