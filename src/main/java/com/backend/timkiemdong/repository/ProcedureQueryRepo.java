package com.backend.timkiemdong.repository;

import com.backend.timkiemdong.dto.ParaCurrencyRateDto;
import com.backend.timkiemdong.dto.SearchInput;
import com.backend.timkiemdong.dto.SearchResult;
import com.backend.timkiemdong.entity.ParaCurrencyRate;

import java.util.List;
import java.util.Map;

public interface ProcedureQueryRepo {
    Map<String,Object> findByProcedure(ParaCurrencyRateDto search, SearchInput searchInput) ;
}
