package com.todocodeacademy.BlogCode.service;

import com.todocodeacademy.BlogCode.dto.PostRequestDTO;
import com.todocodeacademy.BlogCode.dto.PostResponseDTO;
import com.todocodeacademy.BlogCode.exception.BadRequestException;
import com.todocodeacademy.BlogCode.mapper.PostMapper;
import com.todocodeacademy.BlogCode.model.Author;
import com.todocodeacademy.BlogCode.model.Post;
import com.todocodeacademy.BlogCode.repository.IAuthorRepository;
import com.todocodeacademy.BlogCode.repository.IPostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {

    private final IPostRepository postRepository;
    private final IAuthorRepository authorRepository;
    private final PostMapper postMapper;

    public PostService(IPostRepository postRepository,
                       IAuthorRepository authorRepository,
                       PostMapper postMapper) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
        this.postMapper = postMapper;
    }

    @Override
    public List<PostResponseDTO> findAll() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<PostResponseDTO> findById(Long id) {
        return postRepository.findById(id)
                .map(postMapper::toResponse);
    }

    @Override
    public PostResponseDTO save(PostRequestDTO requestDTO) {
        Post post = postMapper.toEntity(requestDTO);
        post.setAuthor(resolveAuthor(requestDTO.authorId()));
        post.setPublicationDate(LocalDate.now());
        Post saved = postRepository.save(post);
        return postMapper.toResponse(saved);
    }

    @Override
    public Optional<PostResponseDTO> update(Long id, PostRequestDTO requestDTO) {
        return postRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(requestDTO.title());
                    existing.setContent(requestDTO.content());
                    existing.setAuthor(resolveAuthor(requestDTO.authorId()));
                    Post updated = postRepository.save(existing);
                    return postMapper.toResponse(updated);
                });
    }

    @Override
    public boolean deleteById(Long id) {
        return postRepository.findById(id)
                .map(post -> {
                    postRepository.delete(post);
                    return true;
                })
                .orElse(false);
    }

    private Author resolveAuthor(Long authorId) {
        if (authorId == null) {
            throw new BadRequestException("Author id is required");
        }
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new BadRequestException("Author not found: " + authorId));
    }
}
