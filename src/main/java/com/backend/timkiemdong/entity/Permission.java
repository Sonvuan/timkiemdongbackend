package com.backend.timkiemdong.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "PERMISSION_PARA")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_para_seq_gen")
    @SequenceGenerator(name = "permission_para_seq_gen", sequenceName = "PERMISSION_PARA_SEQ", allocationSize = 1)
    private Long id;

    private String name;
    private String description;
}
