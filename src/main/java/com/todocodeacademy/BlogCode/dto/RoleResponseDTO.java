package com.todocodeacademy.BlogCode.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.util.Set;

@Builder
@JsonPropertyOrder({"id", "role", "permissions"})
public record RoleResponseDTO(
        Long id,
        String role,
        Set<String> permissions
) {}
