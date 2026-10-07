package com.todocodeacademy.BlogCode.service;

import com.todocodeacademy.BlogCode.dto.AuthorRequestDTO;
import com.todocodeacademy.BlogCode.dto.AuthorResponseDTO;
import com.todocodeacademy.BlogCode.mapper.AuthorMapper;
import com.todocodeacademy.BlogCode.model.Author;
import com.todocodeacademy.BlogCode.repository.IAuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements IAuthorService {

    private final IAuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(IAuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorResponseDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<AuthorResponseDTO> findById(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toResponse);
    }

    @Override
    public AuthorResponseDTO save(AuthorRequestDTO requestDTO) {
        Author author = authorMapper.toEntity(requestDTO);
        Author saved = authorRepository.save(author);
        return authorMapper.toResponse(saved);
    }

    @Override
    public Optional<AuthorResponseDTO> update(Long id, AuthorRequestDTO requestDTO) {
        return authorRepository.findById(id)
                .map(existing -> {
                    existing.setName(requestDTO.name());
                    existing.setLastName(requestDTO.lastName());
                    existing.setEmail(requestDTO.email());
                    Author updated = authorRepository.save(existing);
                    return authorMapper.toResponse(updated);
                });
    }

    @Override
    public boolean deleteById(Long id) {
        return authorRepository.findById(id)
                .map(author -> {
                    authorRepository.delete(author);
                    return true;
                })
                .orElse(false);
    }
}
