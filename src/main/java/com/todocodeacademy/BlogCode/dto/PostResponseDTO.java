package com.todocodeacademy.BlogCode.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonPropertyOrder({"id", "title", "content", "publicationDate", "authorId", "authorFullName"})
public record PostResponseDTO(
        Long id,
        String title,
        String content,
        LocalDate publicationDate,
        Long authorId,
        String authorFullName
) {}
