package com.backend.timkiemdong.service;

import com.backend.timkiemdong.dto.ParaCurrencyRateDto;
import com.backend.timkiemdong.dto.SearchInput;
import com.backend.timkiemdong.dto.SearchResult;
import com.backend.timkiemdong.entity.ParaCurrencyRate;

import java.util.List;
import java.util.Map;

public interface ParaCurrencyRateService {
    SearchResult findList(SearchInput searchInput);

    ParaCurrencyRateDto create(ParaCurrencyRateDto paraCurrencyRateDto);

    ParaCurrencyRateDto update(ParaCurrencyRateDto paraCurrencyRateDto);

    //    ParaCurrencyRateDto update(Long id, ParaCurrencyRateDto paraCurrencyRateDto);

    void delete(ParaCurrencyRate paraCurrencyRate);

//    void delete(Long id);

    SearchResult findBySpec(ParaCurrencyRateDto paraCurrencyRateDto, SearchInput criteria);

    Map<String, Object> findByNat(ParaCurrencyRateDto paraCurrencyRateDto, SearchInput searchInput);

    Map<String, Object> finByPro(ParaCurrencyRateDto paraCurrencyRateDto, SearchInput searchInput);

    List<ParaCurrencyRate> getAll(ParaCurrencyRateDto paraCurrencyRateDto);

}
