package com.todocodeacademy.BlogCode.service;

import com.todocodeacademy.BlogCode.dto.PermissionRequestDTO;
import com.todocodeacademy.BlogCode.dto.PermissionResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {

    List<PermissionResponseDTO> findAll();
    Optional<PermissionResponseDTO> findById(Long id);
    PermissionResponseDTO save(PermissionRequestDTO requestDTO);
    Optional<PermissionResponseDTO> update(Long id, PermissionRequestDTO requestDTO);
    boolean deleteById(Long id);

}
