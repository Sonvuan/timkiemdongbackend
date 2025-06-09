package com.backend.timkiemdong.dto;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcountRequest {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String permission;
//    private Set<String> role;
//    private Set<String> permission;
}
