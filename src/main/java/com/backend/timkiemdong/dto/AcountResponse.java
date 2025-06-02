package com.backend.timkiemdong.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcountResponse {
    private String name;
    private String email;

}
