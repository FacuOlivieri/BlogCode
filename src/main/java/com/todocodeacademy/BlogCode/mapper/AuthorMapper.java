package com.todocodeacademy.BlogCode.mapper;

import com.todocodeacademy.BlogCode.dto.AuthorRequestDTO;
import com.todocodeacademy.BlogCode.dto.AuthorResponseDTO;
import com.todocodeacademy.BlogCode.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author toEntity(AuthorRequestDTO requestDTO) {
        return Author.builder()
                .name(requestDTO.name())
                .lastName(requestDTO.lastName())
                .email(requestDTO.email())
                .build();
    }

    public AuthorResponseDTO toResponse(Author author) {
        return AuthorResponseDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .lastName(author.getLastName())
                .email(author.getEmail())
                .build();
    }
}
