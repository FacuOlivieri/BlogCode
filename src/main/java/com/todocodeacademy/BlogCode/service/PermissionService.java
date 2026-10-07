package com.todocodeacademy.BlogCode.service;

import com.todocodeacademy.BlogCode.dto.PermissionRequestDTO;
import com.todocodeacademy.BlogCode.dto.PermissionResponseDTO;
import com.todocodeacademy.BlogCode.mapper.PermissionMapper;
import com.todocodeacademy.BlogCode.model.Permission;
import com.todocodeacademy.BlogCode.repository.IPermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService implements IPermissionService {

    private final IPermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionService(IPermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<PermissionResponseDTO> findAll() {
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<PermissionResponseDTO> findById(Long id) {
        return permissionRepository.findById(id)
                .map(permissionMapper::toResponse);
    }

    @Override
    public PermissionResponseDTO save(PermissionRequestDTO requestDTO) {
        Permission permission = permissionMapper.toEntity(requestDTO);
        Permission saved = permissionRepository.save(permission);
        return permissionMapper.toResponse(saved);
    }

    @Override
    public Optional<PermissionResponseDTO> update(Long id, PermissionRequestDTO requestDTO) {
        return permissionRepository.findById(id)
                .map(existing -> {
                    existing.setPermissionName(requestDTO.permissionName());
                    Permission updated = permissionRepository.save(existing);
                    return permissionMapper.toResponse(updated);
                });
    }

    @Override
    public boolean deleteById(Long id) {
        return permissionRepository.findById(id)
                .map(permission -> {
                    permissionRepository.delete(permission);
                    return true;
                })
                .orElse(false);
    }
}
