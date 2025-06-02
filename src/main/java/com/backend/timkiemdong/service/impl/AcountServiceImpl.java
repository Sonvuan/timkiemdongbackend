package com.backend.timkiemdong.service.impl;

import com.backend.timkiemdong.dto.AcountRequest;
import com.backend.timkiemdong.dto.AcountResponse;
import com.backend.timkiemdong.entity.Acount;
import com.backend.timkiemdong.mapper.AcountMapper;
import com.backend.timkiemdong.repository.AcountRepository;
import com.backend.timkiemdong.service.AcountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AcountServiceImpl implements AcountService {
    @Autowired
    private AcountRepository acountRepository;
    @Autowired
    private AcountMapper acountMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AcountResponse createAcount(AcountRequest acountRequest){
            Acount acount = acountMapper.toAcount(acountRequest);
            acount.setPassword(passwordEncoder.encode(acountRequest.getPassword()));
            if(!acountRepository.existsByEmail(acountRequest.getEmail())){
                acountRepository.save(acount);
                return acountMapper.toAcountResponse(acount);
            }
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Already Exist");

    }
}
