package com.todocodeacademy.BlogCode.dto;

import java.util.Set;

public record UserSecRequestDTO(
        String username,
        String password,
        Set<Long> roleIds
) {}
