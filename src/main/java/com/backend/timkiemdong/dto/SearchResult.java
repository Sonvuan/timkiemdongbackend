package com.backend.timkiemdong.dto;

import com.backend.timkiemdong.entity.ParaCurrencyRate;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
    private String sortBy;
    private String sortDirection;
    private List<ParaCurrencyRate> content = new ArrayList<>();
}
