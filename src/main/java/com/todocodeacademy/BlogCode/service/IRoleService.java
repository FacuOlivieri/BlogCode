package com.todocodeacademy.BlogCode.service;

import com.todocodeacademy.BlogCode.dto.RoleRequestDTO;
import com.todocodeacademy.BlogCode.dto.RoleResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    List<RoleResponseDTO> findAll();
    Optional<RoleResponseDTO> findById(Long id);
    RoleResponseDTO save(RoleRequestDTO requestDTO);
    Optional<RoleResponseDTO> update(Long id, RoleRequestDTO requestDTO);
    boolean deleteById(Long id);

}
