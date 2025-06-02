package com.backend.timkiemdong.dto;

import lombok.*;

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
}
