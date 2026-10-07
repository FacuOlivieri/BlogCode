package com.todocodeacademy.BlogCode.dto;

import java.util.Set;

public record RoleRequestDTO(
        String role,
        Set<Long> permissionIds
) {}
