package com.backend.timkiemdong.service;

import com.backend.timkiemdong.dto.AcountRequest;
import com.backend.timkiemdong.dto.AcountResponse;

public interface AcountService {
    AcountResponse createAcount(AcountRequest accountRequest);
}
