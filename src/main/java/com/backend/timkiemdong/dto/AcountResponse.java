package com.backend.timkiemdong.dto;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcountResponse {
    private String name;
    private String email;
        private Set<String> roles;
    private Set<String> permissions;
//    private String roles;
//    private String permissions;

}
