package com.backend.timkiemdong.service;

import com.backend.timkiemdong.entity.Country;

import java.util.List;

public interface CountryService {
    List<Country> findAll();
    Country create(Country country);
}
