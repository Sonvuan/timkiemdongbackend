package com.backend.timkiemdong.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;


import java.util.Set;

@Entity(name = "ACOUNT")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Acount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acount_seq_gen")
    @SequenceGenerator(name = "acount_seq_gen", sequenceName = "ACOUNT_SEQ", allocationSize = 1)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "acount_roles",
            joinColumns = @JoinColumn(name = "acount_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
