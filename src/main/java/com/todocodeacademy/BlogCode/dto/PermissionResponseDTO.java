package com.todocodeacademy.BlogCode.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@Builder
@JsonPropertyOrder({"id", "permissionName"})
public record PermissionResponseDTO(
        Long id,
        String permissionName
) {}
