package com.backend.timkiemdong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParaCurrencyRateDto {
    private Long id;
    private String currency;
    private String currencyName;
    private Long decimalPrecision;
    private LocalDate currencyHoliday;
    private String fcPurchase;
    private String fcSale;
    private String cashTransaction;
    private String currencyExchange;
    private Long vndGlAccount;
    private Long fcGlAccount;
    private String country;
    private Long buyingRate;
    private Long brCashHigh;
    private Long brCashLow;
    private Long sellingRate;
    private Long intermediateRate;
    private Integer paraStatus;
    private Integer activeStatus;
    private String jsonData;



}
