package com.backend.timkiemdong.service.impl;


import com.backend.timkiemdong.entity.Acount;
import com.backend.timkiemdong.repository.AcountRepository;

import com.backend.timkiemdong.service.AppUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AppUserDetailServiceImpl implements UserDetailsService, AppUserDetailService {

    @Autowired
    private AcountRepository acountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Acount existing = acountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found for the email:"+email));
        ;
        return new User(existing.getEmail(),existing.getPassword(),new ArrayList<>());
    }


}
