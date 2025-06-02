package com.backend.timkiemdong.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "COUNTRY")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_seq_gen")
    @SequenceGenerator(name = "country_seq_gen", sequenceName = "COUNTRY_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "Ma")
    private String ma;


    @Column(name = "NAME")
    private String name;
//
//    @OneToMany(mappedBy = "country")
//    private List<ParaCurrencyRate> paraCurrencyRates;
}
