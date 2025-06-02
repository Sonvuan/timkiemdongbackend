package com.backend.timkiemdong.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "PARA_CURRENCY_RATE")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParaCurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_seq_gen")
    @SequenceGenerator(name = "currency_seq_gen", sequenceName = "CURRENCY_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "CURRENCY_NAME")
    private String currencyName;

    @Column(name = "DECIMAL_PRECISION")
    private Long decimalPrecision;

    @Column(name = "CURRENCY_HOLIDAY")
    private LocalDate currencyHoliday;

    @Column(name = "FC_PURCHASE")
    private String fcPurchase;

    @Column(name = "FC_SALE")
    private String fcSale;

    @Column(name = "CASH_TRANSACTION")
    private String cashTransaction;

    @Column(name = "CURRENCY_EXCHANGE")
    private String currencyExchange;

    @Column(name = "VND_GL_ACCOUNT")
    private Long vndGlAccount;

    @Column(name = "FC_GL_ACCOUNT")
    private Long fcGlAccount;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "BUYING_RATE")
    private Long buyingRate;

    @Column(name = "BR_CASH_HIGH")
    private Long brCashHigh;

    @Column(name = "BR_CASH_LOW")
    private Long brCashLow;

    @Column(name = "SELLING_RATE")
    private Long sellingRate;

    @Column(name = "INTERMEDIATE_RATE")
    private Long intermediateRate;

    @Column(name = "PARA_STATUS")
    private Integer paraStatus;

    @Column(name = "ACTIVE_STATUS")
    private Integer activeStatus;

    @Column(name = "JSON_DATA")
    private String jsonData;



}
