package com.backend.timkiemdong.service;

import com.backend.timkiemdong.dto.AcountRequest;
import com.backend.timkiemdong.dto.AcountResponse;
import com.backend.timkiemdong.dto.AuthResponse;
import com.backend.timkiemdong.dto.LoginRequest;

public interface AcountService {
    AuthResponse login(LoginRequest loginRequest);
    AcountResponse createAcount(AcountRequest accountRequest);
    AcountResponse updateRole(AcountRequest acountRequest);
    AcountResponse updatePermission (AcountRequest acountRequest);
    AcountResponse removePermission(AcountRequest acountRequest);
}
