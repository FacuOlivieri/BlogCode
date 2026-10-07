package com.todocodeacademy.BlogCode.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.util.Set;

@Builder
@JsonPropertyOrder({"id", "username", "enabled", "roles"})
public record UserSecResponseDTO(
        Long id,
        String username,
        boolean enabled,
        Set<String> roles
) {}
