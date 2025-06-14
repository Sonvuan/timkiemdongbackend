package com.backend.timkiemdong.service.impl;

import com.backend.timkiemdong.entity.Country;
import com.backend.timkiemdong.repository.CountryRepository;
import com.backend.timkiemdong.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired private CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }
    @Override
    public Country create(Country country) {
        return countryRepository.save(country);
    }
}
