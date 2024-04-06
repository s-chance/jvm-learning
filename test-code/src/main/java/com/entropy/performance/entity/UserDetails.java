package com.entropy.performance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class UserDetails {
    private Long id;
    private LocalDateTime register;
    private Date register2;
}
