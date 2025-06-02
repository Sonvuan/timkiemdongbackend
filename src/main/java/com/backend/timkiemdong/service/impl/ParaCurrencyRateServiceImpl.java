package com.backend.timkiemdong.service.impl;

import com.backend.timkiemdong.dto.ParaCurrencyRateDto;
import com.backend.timkiemdong.dto.SearchInput;
import com.backend.timkiemdong.dto.SearchResult;
import com.backend.timkiemdong.entity.ParaCurrencyRate;
import com.backend.timkiemdong.mapper.ParaCurrencyRateMapper;
import com.backend.timkiemdong.repository.ParaCurrencyRateRepository;
import com.backend.timkiemdong.repository.impl.SpecificationRepoImpl;
import com.backend.timkiemdong.service.ParaCurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ParaCurrencyRateServiceImpl implements ParaCurrencyRateService {
    @Autowired
    private ParaCurrencyRateRepository paraCurrencyRateRepository;
    @Autowired
    private ParaCurrencyRateMapper paraCurrencyRateMapper;


    @Override
    public List<ParaCurrencyRate> getAll(ParaCurrencyRateDto paraCurrencyRateDto) {
        return paraCurrencyRateRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }


    // hiển thị dữ liệu
    @Override
    public SearchResult findList(SearchInput searchInput) {
        int page = searchInput.getPage();
        int size = searchInput.getSize();
        if (page < 0) {
            page = 0;
            searchInput.setPage(page);
        } else if (size <= 0) {
            size = 5;
            searchInput.setSize(size);
        }
        String sortBy = searchInput.getSortBy();
        String sortDirection = searchInput.getSortDirection();

        if (sortBy == null || sortBy.trim().isEmpty()) {
            sortBy = "id";
            searchInput.setSortBy(sortBy);
        }

        if (sortDirection == null || sortDirection.trim().isEmpty()) {
            sortDirection = "asc";
            searchInput.setSortDirection(sortDirection);
        }

        if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
            sortDirection = "asc";
            searchInput.setSortDirection(sortDirection);
        }

        Sort sort = Sort.by(Sort.Direction.fromString(searchInput.getSortDirection()), searchInput.getSortBy());
        Pageable pageable = PageRequest.of(page, size,sort);
        Page<ParaCurrencyRate> pagegb = paraCurrencyRateRepository.findAll(pageable);
        SearchResult searchResult = new SearchResult();
        searchResult.setSize(searchInput.getSize());
        searchResult.setPage(searchInput.getPage());
        searchResult.setTotalPages(pagegb.getTotalPages());
        searchResult.setSortBy(searchInput.getSortBy());
        searchResult.setSortDirection(searchInput.getSortDirection());
        searchResult.setTotalElements(pagegb.getTotalElements());
        searchResult.setContent(pagegb.getContent());
        return searchResult;
    }



    // thêm mới
    @Override
    public ParaCurrencyRateDto create(ParaCurrencyRateDto paraCurrencyRateDto) {
        ParaCurrencyRate paraCurrencyRate = paraCurrencyRateMapper.toEntity(paraCurrencyRateDto);
        ParaCurrencyRate createPra = paraCurrencyRateRepository.save(paraCurrencyRate);
        return paraCurrencyRateMapper.toDTO(createPra);
    }

    // sửa
    @Override
    public ParaCurrencyRateDto update( ParaCurrencyRateDto paraCurrencyRateDto) {
        long id = paraCurrencyRateDto.getId();
        ParaCurrencyRate existingEntity = paraCurrencyRateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy với ID: " + id));
        paraCurrencyRateMapper.updateEntityFromDto(paraCurrencyRateDto, existingEntity);
        ParaCurrencyRate updatedEntity = paraCurrencyRateRepository.save(existingEntity);
        return paraCurrencyRateMapper.toDTO(updatedEntity);
    }

//    @Override
//    public ParaCurrencyRateDto update(Long id, ParaCurrencyRateDto paraCurrencyRateDto) {
//        ParaCurrencyRate existingEntity = paraCurrencyRateRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy với ID: " + id));
//        paraCurrencyRateMapper.updateEntityFromDto(paraCurrencyRateDto, existingEntity);
//        ParaCurrencyRate updatedEntity = paraCurrencyRateRepository.save(existingEntity);
//        return paraCurrencyRateMapper.toDTO(updatedEntity);
//    }


    // xoá
    @Override
    public void delete(ParaCurrencyRate paraCurrencyRate) {
        Long id = paraCurrencyRate.getId();
        if (paraCurrencyRateRepository.existsById(id)) {
            paraCurrencyRateRepository.deleteById(id);
            return;
        }
        throw new RuntimeException("ID không tồn tại");
    }

//    @Override
//    public void delete( Long id) {
//        if (paraCurrencyRateRepository.existsById(id)) {
//            paraCurrencyRateRepository.deleteById(id);
//            return;
//        }
//        throw new RuntimeException("ID không tồn tại");
//    }


    // tìm kiếm bằng spectification
    @Override
    public SearchResult findBySpec(ParaCurrencyRateDto paraCurrencyRateDto, SearchInput searchInput) {
        try {
            int page = searchInput.getPage();
            int size = searchInput.getSize();
            if (page < 0) {
                page = 0;
                searchInput.setPage(page);
            } else if (size <= 0) {
                size = 5;
                searchInput.setSize(size);
            }
            String sortBy = searchInput.getSortBy();
            String sortDirection = searchInput.getSortDirection();

            if (sortBy == null || sortBy.trim().isEmpty()) {
                sortBy = "id";
                searchInput.setSortBy(sortBy);
            }

            if (sortDirection == null || sortDirection.trim().isEmpty()) {
                sortDirection = "asc";
                searchInput.setSortDirection(sortDirection);
            }

            if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
                sortDirection = "asc";
                searchInput.setSortDirection(sortDirection);
            }

            Sort sort = Sort.by(Sort.Direction.fromString(searchInput.getSortDirection()), searchInput.getSortBy());
            Pageable pageable = PageRequest.of(page, size,sort);
            Specification<ParaCurrencyRate> spec = new SpecificationRepoImpl(paraCurrencyRateDto, searchInput);
            Page<ParaCurrencyRate> pageg = paraCurrencyRateRepository.findAll(spec, pageable);

            SearchResult result = new SearchResult();
            result.setTotalPages(pageg.getTotalPages());
            result.setTotalElements(pageg.getTotalElements());
            result.setPage(searchInput.getPage());
            result.setSize(searchInput.getSize());
            result.setSortBy(searchInput.getSortBy());
            result.setSortDirection(searchInput.getSortDirection());
            result.setContent(pageg.getContent());
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể tìm kiếm: " + e.getMessage(), e);
        }
    }


    // tìm kim bằng native
    @Override
    public Map<String, Object> findByNat(ParaCurrencyRateDto paraCurrencyRateDto, SearchInput searchInput) {
        try {
            int page = searchInput.getPage();
            int size = searchInput.getSize();
            if (page < 0) {
                page = 0;
                searchInput.setPage(page);
            } else if (size <= 0) {
                size = 5;
                searchInput.setSize(size);
            }

            Map<String, Object> rawResult = paraCurrencyRateRepository.findByNative(paraCurrencyRateDto, searchInput);
            List<ParaCurrencyRate> results = (List<ParaCurrencyRate>) rawResult.get("results");
            long totalElements = (long) rawResult.get("totalElements");


            int totalPages = (int) Math.ceil((double) totalElements / size);

            // Trả về Map
            Map<String, Object> response = new HashMap<>();
            response.put("page", page);
            response.put("size", size);
            response.put("totalElements", totalElements);
            response.put("totalPages", totalPages);
            response.put("sortBy", searchInput.getSortBy());
            response.put("sortDirection", searchInput.getSortDirection());
            response.put("content", results);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể tìm kiếm: " + e.getMessage(), e);
        }

    }


    // tìm kiếm bằng procedure
    @Override
    public Map<String, Object> finByPro(ParaCurrencyRateDto paraCurrencyRateDto, SearchInput searchInput) {
        try {
            int page = searchInput.getPage();
            int size = searchInput.getSize();
            if (page < 0) {
                page = 0;
                searchInput.setPage(0);
            } else if (size <= 0) {
                size = 5;
                searchInput.setSize(size);
            }
            Map<String, Object> rawResult = paraCurrencyRateRepository.findByProcedure(paraCurrencyRateDto, searchInput);
            List<ParaCurrencyRate> results = (List<ParaCurrencyRate>) rawResult.get("results");
            long totalElements = (Long) rawResult.get("totalElements");
            int totalPages = (int) Math.ceil((double) totalElements / size);

            // Trả về Map
            Map<String, Object> response = new HashMap<>();
            response.put("page", page);
            response.put("size", size);
            response.put("totalElements", totalElements);
            response.put("totalPages", totalPages);
            response.put("sortBy", searchInput.getSortBy());
            response.put("sortDirection", searchInput.getSortDirection());
            response.put("content", results);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể tìm kiếm: " + e.getMessage(), e);
        }
    }
}
