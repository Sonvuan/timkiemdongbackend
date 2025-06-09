package com.backend.timkiemdong.service.impl;


import com.backend.timkiemdong.entity.Acount;
import com.backend.timkiemdong.repository.AcountRepository;

import com.backend.timkiemdong.service.AppUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppUserDetailServiceImpl implements UserDetailsService, AppUserDetailService {

    @Autowired
    private AcountRepository acountRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//       Acount existing = acountRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Email not found for the email:"+email));
//        ;
//        return new User(existing.getEmail(),existing.getPassword(),new ArrayList<>());
//    }


//@Override
//public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//    Acount existing = acountRepository.findByEmail(email)
//            .orElseThrow(() -> new UsernameNotFoundException("Email not found for the email: " + email));
//
//    // Lấy danh sách quyền từ vai trò (roles)
//    List<GrantedAuthority> authorities = existing.getRoles().stream()
//            .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName()))
//            .collect(Collectors.toList());
//
//
//    return new User(existing.getEmail(), existing.getPassword(), authorities);
//}


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Acount existing = acountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found for the email: " + email));

        // Lấy Role và Permission cùng làm GrantedAuthority
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Thêm Role với prefix "ROLE_"
        existing.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));

            // Thêm từng permission của role
            if (role.getPermissions() != null) {
                role.getPermissions().forEach(permission -> {
                    authorities.add(new SimpleGrantedAuthority(permission.getName()));
                });
            }
        });

        return new User(existing.getEmail(), existing.getPassword(), authorities);
    }


}
