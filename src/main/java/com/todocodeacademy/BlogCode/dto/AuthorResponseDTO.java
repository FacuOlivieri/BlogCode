package com.todocodeacademy.BlogCode.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@Builder
@JsonPropertyOrder({"id", "name", "lastName", "email"})
public record AuthorResponseDTO(
        Long id,
        String name,
        String lastName,
        String email
) {}
