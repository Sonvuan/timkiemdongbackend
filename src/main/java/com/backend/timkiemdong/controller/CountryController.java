package com.backend.timkiemdong.controller;

import com.backend.timkiemdong.entity.Country;
import com.backend.timkiemdong.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Country")
@CrossOrigin(origins = "http://localhost:4200")
public class CountryController {
    @Autowired
    CountryService countryService;


   @PostMapping("/getAll")
    public List<Country> list() {
       return countryService.findAll();
   }
   @PostMapping("/create")
    public Country create(@RequestBody Country country) {
       return countryService.create(country);
   }
}
