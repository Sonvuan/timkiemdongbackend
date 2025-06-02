package com.backend.timkiemdong.repository;

import com.backend.timkiemdong.dto.ParaCurrencyRateDto;
import com.backend.timkiemdong.dto.SearchInput;
import com.backend.timkiemdong.dto.SearchResult;
import com.backend.timkiemdong.entity.ParaCurrencyRate;


import java.util.List;
import java.util.Map;

public interface NativeQueryRepository {
        Map<String,Object> findByNative(ParaCurrencyRateDto search, SearchInput searchInput);

}
