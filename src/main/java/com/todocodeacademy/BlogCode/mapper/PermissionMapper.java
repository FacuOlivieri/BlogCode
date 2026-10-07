package com.todocodeacademy.BlogCode.mapper;

import com.todocodeacademy.BlogCode.dto.PermissionRequestDTO;
import com.todocodeacademy.BlogCode.dto.PermissionResponseDTO;
import com.todocodeacademy.BlogCode.model.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {

    public Permission toEntity(PermissionRequestDTO requestDTO) {
        return Permission.builder()
                .permissionName(requestDTO.permissionName())
                .build();
    }

    public PermissionResponseDTO toResponse(Permission permission) {
        return PermissionResponseDTO.builder()
                .id(permission.getId())
                .permissionName(permission.getPermissionName())
                .build();
    }
}
