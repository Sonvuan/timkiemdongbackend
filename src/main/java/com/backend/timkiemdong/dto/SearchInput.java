package com.backend.timkiemdong.dto;

import com.backend.timkiemdong.entity.ParaCurrencyRate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchInput {
    private ParaCurrencyRateDto search;
    private LocalDate currencyHolidayFrom;
    private LocalDate currencyHolidayTo;
    private int page=0 ;
    private int size =5;
    private int totalPages;
    private long totalElements;
    private String sortBy="id";
    private String sortDirection="DESC";
    private List<ParaCurrencyRate> content = new ArrayList<>();
}
