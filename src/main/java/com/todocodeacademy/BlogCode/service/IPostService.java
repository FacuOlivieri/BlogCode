package com.todocodeacademy.BlogCode.service;

import com.todocodeacademy.BlogCode.dto.PostRequestDTO;
import com.todocodeacademy.BlogCode.dto.PostResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IPostService {

    List<PostResponseDTO> findAll();
    Optional<PostResponseDTO> findById(Long id);
    PostResponseDTO save(PostRequestDTO requestDTO);
    Optional<PostResponseDTO> update(Long id, PostRequestDTO requestDTO);
    boolean deleteById(Long id);

}
