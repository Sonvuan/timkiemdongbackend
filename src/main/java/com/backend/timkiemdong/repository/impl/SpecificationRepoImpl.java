package com.backend.timkiemdong.repository.impl;

import com.backend.timkiemdong.dto.ParaCurrencyRateDto;
import com.backend.timkiemdong.dto.SearchInput;
import com.backend.timkiemdong.entity.ParaCurrencyRate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SpecificationRepoImpl implements Specification<ParaCurrencyRate> {

    private ParaCurrencyRateDto search;
    private  SearchInput searchInput;


    @Override
    public Predicate toPredicate(Root<ParaCurrencyRate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        try {
            List<Predicate> predicates = new ArrayList<>();
            if (search != null) {
                if (search.getCurrency() != null && !search.getCurrency().trim().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("currency")), "%" + search.getCurrency().toLowerCase() + "%"));
                }
                if (search.getCurrencyName() != null && !search.getCurrencyName().trim().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("currencyName")), "%" + search.getCurrencyName().toLowerCase() + "%"));
                }
                if (search.getDecimalPrecision() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("decimalPrecision"), search.getDecimalPrecision()));
                }
                if (search.getCurrencyHoliday() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("currencyHoliday"), search.getCurrencyHoliday()));
                }
                if (search.getFcPurchase() != null && !search.getFcPurchase().trim().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fcPurchase")), "%" + search.getFcPurchase().toLowerCase() + "%"));
                }
                if (search.getFcSale() != null && !search.getFcSale().trim().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fcSale")), "%" + search.getFcSale().toLowerCase() + "%"));
                }
                if (search.getCashTransaction() != null && !search.getCashTransaction().trim().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("cashTransaction")), "%" + search.getCashTransaction().toLowerCase() + "%"));
                }
                if (search.getCurrencyExchange() != null && !search.getCurrencyExchange().trim().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("currencyExchange")), "%" + search.getCurrencyExchange().toLowerCase() + "%"));
                }
                if (search.getVndGlAccount() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("vndGlAccount"), search.getVndGlAccount()));
                }
                if (search.getFcGlAccount() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("fcGlAccount"), search.getFcGlAccount()));
                }
                if (search.getCountry() != null && !search.getCountry().trim().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("country")), "%" + search.getCountry().toLowerCase() + "%"));
                }
                if (search.getBuyingRate() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("buyingRate"), search.getBuyingRate()));
                }
                if (search.getBrCashLow() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("brCashLow"), search.getBrCashLow()));
                }
                if (search.getBrCashHigh() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("brCashHigh"), search.getBrCashHigh()));
                }
                if (search.getSellingRate() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("sellingRate"), search.getSellingRate()));
                }
                if (search.getIntermediateRate() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("intermediateRate"), search.getIntermediateRate()));
                }
                if (search.getParaStatus() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("paraStatus"), search.getParaStatus()));
                }
                if (search.getActiveStatus() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("activeStatus"), search.getActiveStatus()));
                }
                if (search.getJsonData() != null && !search.getJsonData().trim().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("jsonData")), "%" + search.getJsonData().toLowerCase() + "%"));
                }

            }
            if (searchInput != null) {
                if (searchInput.getCurrencyHolidayFrom() != null && searchInput.getCurrencyHolidayTo() != null) {
                    predicates.add(criteriaBuilder.between(root.get("currencyHoliday"), searchInput.getCurrencyHolidayFrom(), searchInput.getCurrencyHolidayTo()));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi thực hiện câu lệnh spetification: " + e.getMessage());
        }

    }

}
