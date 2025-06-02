package com.backend.timkiemdong.repository.impl;

import com.backend.timkiemdong.dto.ParaCurrencyRateDto;
import com.backend.timkiemdong.dto.SearchInput;
import com.backend.timkiemdong.dto.SearchResult;
import com.backend.timkiemdong.entity.ParaCurrencyRate;
import com.backend.timkiemdong.repository.NativeQueryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class NativeQueryRepositoryImpl implements NativeQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, Object> findByNative(ParaCurrencyRateDto search, SearchInput searchInput) {
        StringBuilder sql = new StringBuilder("select p.id, p.currency, p.currency_name, p.decimal_precision, p.currency_holiday, \n" +
                "    p.fc_purchase, p.fc_sale, p.cash_transaction, p.currency_exchange, \n" +
                "    p.vnd_gl_account, p.fc_gl_account, p.country, \n" +
                "    p.buying_rate, p.br_cash_high, p.br_cash_low, \n" +
                "    p.selling_rate, p.intermediate_rate, \n" +
                "    p.para_status, p.active_status, p.json_data, \n" +
                "    c.ma from para_currency_rate p left join country c on p.country = c.name where 1=1 ");
//        StringBuilder sql = new StringBuilder("select p.*, c.ma from para_currency_rate p left join country c on p.country = c.name where 1=1 ");
//        StringBuilder sql = new StringBuilder("select * from Para_Currency_Rate where 1=1 ");
        StringBuilder countSql = new StringBuilder("SELECT count(1) FROM Para_Currency_Rate WHERE 1=1 ");
        Map<String, Object> params = new HashMap<>();
        try {
            if (search != null) {
                if (search.getCurrency() != null && !search.getCurrency().trim().isEmpty()) {
                    sql.append(" AND lower(currency) LIKE :currency");
                    countSql.append(" AND lower(currency) LIKE :currency");
                    params.put("currency", "%" + search.getCurrency().toLowerCase() + "%");
                }
                if (search.getCurrencyName() != null && !search.getCurrencyName().trim().isEmpty()) {
                    sql.append(" AND lower(currency_Name) LIKE :currency_Name");
                    countSql.append(" AND lower(currency_Name) LIKE :currency_Name");
                    params.put("currency_Name", "%" + search.getCurrencyName().toLowerCase() + "%");
                }
                if (search.getCurrencyExchange() != null && !search.getCurrencyExchange().trim().isEmpty()) {
                    sql.append(" AND lower(currency_Exchange) LIKE :currency_Exchange");
                    countSql.append(" AND lower(currency_Exchange) LIKE :currency_Exchange");
                    params.put("currency_Exchange", "%" + search.getCurrencyExchange().toLowerCase() + "%");
                }
                if (search.getCurrencyHoliday() != null) {
                    sql.append(" AND currency_holiday = :currency_holiday");
                    countSql.append(" AND currency_holiday = :currency_holiday");
                    params.put("currency_holiday", search.getCurrencyHoliday());
                }
                if (search.getDecimalPrecision() != null) {
                    sql.append(" AND DECIMAL_PRECISION = :DECIMAL_PRECISION");
                    countSql.append(" AND DECIMAL_PRECISION = :DECIMAL_PRECISION");
                    params.put("DECIMAL_PRECISION", search.getDecimalPrecision());
                }
                if (search.getFcGlAccount() != null) {
                    sql.append(" AND fc_Gl_Account = :fc_Gl_Account");
                    countSql.append(" AND fc_Gl_Account = :fc_Gl_Account");
                    params.put("fc_Gl_Account", search.getFcGlAccount());
                }
                if (search.getFcPurchase() != null && !search.getFcPurchase().trim().isEmpty()) {
                    sql.append(" AND lower(fc_Purchase) LIKE :fc_Purchase");
                    countSql.append(" AND lower(fc_Purchase) LIKE :fc_Purchase");
                    params.put("fc_Purchase", "%" + search.getFcPurchase().toLowerCase() + "%");
                }
                if (search.getFcSale() != null && !search.getFcSale().trim().isEmpty()) {
                    sql.append(" AND lower(fc_Sale) LIKE :fc_Sale");
                    countSql.append(" AND lower(fc_Sale) LIKE :fc_Sale");
                    params.put("fc_Sale", "%" + search.getFcSale().toLowerCase() + "%");
                }
                if (search.getIntermediateRate() != null) {
                    sql.append(" AND intermediate_Rate = :intermediate_Rate");
                    countSql.append(" AND intermediate_Rate = :intermediate_Rate");
                    params.put("intermediate_Rate", search.getIntermediateRate());
                }
                if (search.getJsonData() != null && !search.getJsonData().trim().isEmpty()) {
                    sql.append(" AND lower(json_data) LIKE :json_data");
                    countSql.append(" AND lower(json_data) LIKE :json_data");
                    params.put("json_data", "%" + search.getJsonData().toLowerCase() + "%");
                }
                if (search.getParaStatus() != null) {
                    sql.append(" AND para_status = :para_status");
                    countSql.append(" AND para_status = :para_status");
                    params.put("para_status", search.getParaStatus());
                }
                if (search.getSellingRate() != null) {
                    sql.append(" AND selling_Rate = :selling_Rate");
                    countSql.append(" AND selling_Rate = :selling_Rate");
                    params.put("selling_Rate", search.getSellingRate());
                }
                if (search.getVndGlAccount() != null) {
                    sql.append(" AND vnd_Gl_Account = :vnd_Gl_Account");
                    countSql.append(" AND vnd_Gl_Account = :vnd_Gl_Account");
                    params.put("vnd_Gl_Account", search.getVndGlAccount());
                }
                if (search.getActiveStatus() != null) {
                    sql.append(" AND active_Status = :active_Status");
                    countSql.append(" AND active_Status = :active_Status");
                    params.put("active_Status", search.getActiveStatus());
                }
                if (search.getBrCashHigh() != null) {
                    sql.append(" AND br_Cash_High = :br_Cash_High");
                    countSql.append(" AND br_Cash_High = :br_Cash_High");
                    params.put("br_Cash_High", search.getBrCashHigh());
                }
                if (search.getBrCashLow() != null) {
                    sql.append(" AND br_Cash_Low = :br_Cash_Low");
                    countSql.append(" AND br_Cash_Low = :br_Cash_Low");
                    params.put("br_Cash_Low", search.getBrCashLow());
                }
                if (search.getBuyingRate() != null) {
                    sql.append(" AND buying_Rate = :buying_Rate");
                    countSql.append(" AND buying_Rate = :buying_Rate");
                    params.put("buying_Rate", search.getBuyingRate());
                }
                if (search.getCountry() != null && !search.getCountry().trim().isEmpty()) {
                    sql.append(" AND lower(country) LIKE :country");
                    countSql.append(" AND lower(country) LIKE :country");
                    params.put("country", "%" + search.getCountry().toLowerCase() + "%");
                }

                if (search.getCashTransaction() != null && !search.getCashTransaction().trim().isEmpty()) {
                    sql.append(" AND lower(cash_Transaction) LIKE :cash_Transaction");
                    countSql.append(" AND lower(cash_Transaction) LIKE :cash_Transaction");
                    params.put("cash_Transaction", "%" + search.getCashTransaction().toLowerCase() + "%");
                }
            }
            if (searchInput.getCurrencyHolidayFrom() != null && searchInput.getCurrencyHolidayTo() != null) {
                sql.append(" AND currency_holiday BETWEEN :currencyHolidayFrom AND :currencyHolidayTo ");
                countSql.append(" AND currency_holiday BETWEEN :currencyHolidayFrom AND :currencyHolidayTo ");
                params.put("currencyHolidayFrom", searchInput.getCurrencyHolidayFrom());
                params.put("currencyHolidayTo", searchInput.getCurrencyHolidayTo());
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

            sql.append(" ORDER BY ").append(sortBy.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase()).append(" ").append(sortDirection.toUpperCase());


            Query query = entityManager.createNativeQuery(sql.toString(), ParaCurrencyRate.class);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            // Count query
            Query countQuery = entityManager.createNativeQuery(countSql.toString());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                countQuery.setParameter(entry.getKey(), entry.getValue());
            }
            List<ParaCurrencyRate> results = query.getResultList();
            Object countResult = countQuery.getSingleResult();
            long totalElements = ((Number) countResult).longValue();

            Map<String, Object> result = new HashMap<>();
            result.put("results", results);
            result.put("sortBy", searchInput.getSortBy());
            result.put("sortDirection", searchInput.getSortDirection());
            result.put("totalElements", totalElements);
            return result;
        } catch (
                Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi query native: " + e.getMessage());
        }
    }
}
