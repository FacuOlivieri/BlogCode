package com.todocodeacademy.BlogCode.service;

import com.todocodeacademy.BlogCode.dto.RoleRequestDTO;
import com.todocodeacademy.BlogCode.dto.RoleResponseDTO;
import com.todocodeacademy.BlogCode.exception.BadRequestException;
import com.todocodeacademy.BlogCode.mapper.RoleMapper;
import com.todocodeacademy.BlogCode.model.Permission;
import com.todocodeacademy.BlogCode.model.Role;
import com.todocodeacademy.BlogCode.repository.IPermissionRepository;
import com.todocodeacademy.BlogCode.repository.IRoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    private final IRoleRepository roleRepository;
    private final IPermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    public RoleService(IRoleRepository roleRepository,
                       IPermissionRepository permissionRepository,
                       RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleResponseDTO> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<RoleResponseDTO> findById(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::toResponse);
    }

    @Override
    public RoleResponseDTO save(RoleRequestDTO requestDTO) {
        Role role = roleMapper.toEntity(requestDTO);
        role.setPermissionsList(resolvePermissions(requestDTO.permissionIds()));
        Role saved = roleRepository.save(role);
        return roleMapper.toResponse(saved);
    }

    @Override
    public Optional<RoleResponseDTO> update(Long id, RoleRequestDTO requestDTO) {
        return roleRepository.findById(id)
                .map(existing -> {
                    existing.setRole(requestDTO.role());
                    if (requestDTO.permissionIds() != null) {
                        existing.setPermissionsList(resolvePermissions(requestDTO.permissionIds()));
                    }
                    Role updated = roleRepository.save(existing);
                    return roleMapper.toResponse(updated);
                });
    }

    @Override
    public boolean deleteById(Long id) {
        return roleRepository.findById(id)
                .map(role -> {
                    roleRepository.delete(role);
                    return true;
                })
                .orElse(false);
    }

    private Set<Permission> resolvePermissions(Set<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return new HashSet<>();
        }
        if (permissionIds.contains(null)) {
            throw new BadRequestException("Permission ids must not contain null");
        }
        Set<Permission> found = new HashSet<>(permissionRepository.findAllById(permissionIds));
        if (found.size() < permissionIds.size()) {
            Set<Long> foundIds = found.stream().map(Permission::getId).collect(Collectors.toSet());
            Set<Long> missing = permissionIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toCollection(TreeSet::new));
            throw new BadRequestException("Permissions not found: " + missing);
        }
        return found;
    }
}
