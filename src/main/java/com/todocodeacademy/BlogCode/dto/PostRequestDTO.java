package com.todocodeacademy.BlogCode.dto;

public record PostRequestDTO(
        String title,
        String content,
        Long authorId
) {}
