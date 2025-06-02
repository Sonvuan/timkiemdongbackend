package com.backend.timkiemdong.repository.impl;

import com.backend.timkiemdong.dto.ParaCurrencyRateDto;
import com.backend.timkiemdong.dto.SearchInput;
import com.backend.timkiemdong.dto.SearchResult;
import com.backend.timkiemdong.entity.ParaCurrencyRate;
import com.backend.timkiemdong.repository.ProcedureQueryRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;


import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcedureQueryRepoImpl implements ProcedureQueryRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String,Object> findByProcedure(ParaCurrencyRateDto search, SearchInput searchInput) {

        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("son_procedure_search_a", ParaCurrencyRate.class);


            query.registerStoredProcedureParameter("p_active_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_br_cash_high", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_br_cash_low", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_buying_rate", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_cash_transaction", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_country", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_currency", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_currency_exchange", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_currency_holiday", Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_currency_name", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_decimal_precision", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_fc_gl_account", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_fc_purchase", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_fc_sale", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_intermediate_rate", Double.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_json_data", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_para_status", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_selling_rate", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_vnd_gl_account", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_currency_holiday_from", Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_currency_holiday_to", Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_page", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_size", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_sort_by", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_sort_direction", String.class, ParameterMode.IN);


            query.registerStoredProcedureParameter("p_total_elements", Long.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_result", void.class, ParameterMode.REF_CURSOR);



            query.setParameter("p_active_status", search.getActiveStatus());
            query.setParameter("p_br_cash_high", search.getBrCashHigh());
            query.setParameter("p_br_cash_low", search.getBrCashLow());
            query.setParameter("p_buying_rate", search.getBuyingRate());
            query.setParameter("p_cash_transaction", search.getCashTransaction());
            query.setParameter("p_country", search.getCountry());
            query.setParameter("p_currency", search.getCurrency());
            query.setParameter("p_currency_exchange", search.getCurrencyExchange());
            query.setParameter("p_currency_holiday", search.getCurrencyHoliday());
            query.setParameter("p_currency_name", search.getCurrencyName());
            query.setParameter("p_decimal_precision", search.getDecimalPrecision());
            query.setParameter("p_fc_gl_account", search.getFcGlAccount());
            query.setParameter("p_fc_purchase", search.getFcPurchase());
            query.setParameter("p_fc_sale", search.getFcSale());
            query.setParameter("p_intermediate_rate", search.getIntermediateRate());
            query.setParameter("p_json_data", search.getJsonData());
            query.setParameter("p_para_status", search.getParaStatus());
            query.setParameter("p_selling_rate", search.getSellingRate());
            query.setParameter("p_vnd_gl_account", search.getVndGlAccount());

            query.setParameter("p_currency_holiday_from", searchInput.getCurrencyHolidayFrom());
            query.setParameter("p_currency_holiday_to", searchInput.getCurrencyHolidayTo());


            query.setParameter("p_page", searchInput.getPage());
            query.setParameter("p_size", searchInput.getSize());

            query.setParameter("p_sort_by", searchInput.getSortBy().replaceAll("([a-z])([A-Z]+)","$1_$2").toLowerCase());
            query.setParameter("p_sort_direction", searchInput.getSortDirection());


            // Thực thi
            query.execute();

            // Lấy kết quả từ cursor
            List<ParaCurrencyRate> results = query.getResultList();

            // Lấy tổng số bản ghi
            long totalElements = (long) query.getOutputParameterValue("p_total_elements");


            Map<String, Object> result = new HashMap<>();
            result.put("results", results);
            result.put("sortBy", searchInput.getSortBy());
            result.put("sortDirection", searchInput.getSortDirection());
            result.put("totalElements", totalElements);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi gọi procedure: " + e.getMessage());
        }
    }


}
