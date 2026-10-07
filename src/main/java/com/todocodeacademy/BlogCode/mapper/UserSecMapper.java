package com.todocodeacademy.BlogCode.mapper;

import com.todocodeacademy.BlogCode.dto.UserSecRequestDTO;
import com.todocodeacademy.BlogCode.dto.UserSecResponseDTO;
import com.todocodeacademy.BlogCode.model.Role;
import com.todocodeacademy.BlogCode.model.UserSec;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserSecMapper {

    // password encoding, account flags and roleIds are handled by the service
    public UserSec toEntity(UserSecRequestDTO requestDTO) {
        return UserSec.builder()
                .username(requestDTO.username())
                .build();
    }

    public UserSecResponseDTO toResponse(UserSec userSec) {
        return UserSecResponseDTO.builder()
                .id(userSec.getId())
                .username(userSec.getUsername())
                .enabled(userSec.isEnabled())
                .roles(userSec.getRolesList().stream()
                        .map(Role::getRole)
                        .collect(Collectors.toSet()))
                .build();
    }
}
