package com.todocodeacademy.BlogCode.service;

import com.todocodeacademy.BlogCode.dto.UserSecRequestDTO;
import com.todocodeacademy.BlogCode.dto.UserSecResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IUserSecService {

    List<UserSecResponseDTO> findAll();
    Optional<UserSecResponseDTO> findById(Long id);
    UserSecResponseDTO save(UserSecRequestDTO requestDTO);
    Optional<UserSecResponseDTO> update(Long id, UserSecRequestDTO requestDTO);
    boolean deleteById(Long id);

}
