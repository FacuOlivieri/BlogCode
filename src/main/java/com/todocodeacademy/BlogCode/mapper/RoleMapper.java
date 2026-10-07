package com.todocodeacademy.BlogCode.mapper;

import com.todocodeacademy.BlogCode.dto.RoleRequestDTO;
import com.todocodeacademy.BlogCode.dto.RoleResponseDTO;
import com.todocodeacademy.BlogCode.model.Permission;
import com.todocodeacademy.BlogCode.model.Role;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleMapper {

    // permissionIds are resolved by the service
    public Role toEntity(RoleRequestDTO requestDTO) {
        return Role.builder()
                .role(requestDTO.role())
                .build();
    }

    public RoleResponseDTO toResponse(Role role) {
        return RoleResponseDTO.builder()
                .id(role.getId())
                .role(role.getRole())
                .permissions(role.getPermissionsList().stream()
                        .map(Permission::getPermissionName)
                        .collect(Collectors.toSet()))
                .build();
    }
}
