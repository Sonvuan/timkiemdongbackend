package com.backend.timkiemdong.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public interface AppUserDetailService {
    UserDetails loadUserByUsername(String email);
}
