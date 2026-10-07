package com.todocodeacademy.BlogCode.service;

import com.todocodeacademy.BlogCode.dto.AuthorRequestDTO;
import com.todocodeacademy.BlogCode.dto.AuthorResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {

    List<AuthorResponseDTO> findAll();
    Optional<AuthorResponseDTO> findById(Long id);
    AuthorResponseDTO save(AuthorRequestDTO requestDTO);
    Optional<AuthorResponseDTO> update(Long id, AuthorRequestDTO requestDTO);
    boolean deleteById(Long id);

}
