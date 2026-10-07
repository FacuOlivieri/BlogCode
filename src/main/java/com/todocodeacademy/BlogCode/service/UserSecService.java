package com.todocodeacademy.BlogCode.service;

import com.todocodeacademy.BlogCode.dto.UserSecRequestDTO;
import com.todocodeacademy.BlogCode.dto.UserSecResponseDTO;
import com.todocodeacademy.BlogCode.exception.BadRequestException;
import com.todocodeacademy.BlogCode.mapper.UserSecMapper;
import com.todocodeacademy.BlogCode.model.Role;
import com.todocodeacademy.BlogCode.model.UserSec;
import com.todocodeacademy.BlogCode.repository.IRoleRepository;
import com.todocodeacademy.BlogCode.repository.IUserSecRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class UserSecService implements IUserSecService {

    private final IUserSecRepository userSecRepository;
    private final IRoleRepository roleRepository;
    private final UserSecMapper userSecMapper;
    private final PasswordEncoder passwordEncoder;

    public UserSecService(IUserSecRepository userSecRepository,
                          IRoleRepository roleRepository,
                          UserSecMapper userSecMapper,
                          PasswordEncoder passwordEncoder) {
        this.userSecRepository = userSecRepository;
        this.roleRepository = roleRepository;
        this.userSecMapper = userSecMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserSecResponseDTO> findAll() {
        return userSecRepository.findAll()
                .stream()
                .map(userSecMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<UserSecResponseDTO> findById(Long id) {
        return userSecRepository.findById(id)
                .map(userSecMapper::toResponse);
    }

    @Override
    public UserSecResponseDTO save(UserSecRequestDTO requestDTO) {
        if (requestDTO.password() == null || requestDTO.password().isBlank()) {
            throw new BadRequestException("Password is required");
        }
        UserSec userSec = userSecMapper.toEntity(requestDTO);
        userSec.setPassword(passwordEncoder.encode(requestDTO.password()));
        userSec.setEnabled(true);
        userSec.setAccountNotExpired(true);
        userSec.setAccountNotLocked(true);
        userSec.setCredentialNotExpired(true);
        userSec.setRolesList(resolveRoles(requestDTO.roleIds()));
        UserSec saved = userSecRepository.save(userSec);
        return userSecMapper.toResponse(saved);
    }

    @Override
    public Optional<UserSecResponseDTO> update(Long id, UserSecRequestDTO requestDTO) {
        return userSecRepository.findById(id)
                .map(existing -> {
                    existing.setUsername(requestDTO.username());
                    if (requestDTO.password() != null && !requestDTO.password().isBlank()) {
                        existing.setPassword(passwordEncoder.encode(requestDTO.password()));
                    }
                    if (requestDTO.roleIds() != null) {
                        existing.setRolesList(resolveRoles(requestDTO.roleIds()));
                    }
                    UserSec updated = userSecRepository.save(existing);
                    return userSecMapper.toResponse(updated);
                });
    }

    @Override
    public boolean deleteById(Long id) {
        return userSecRepository.findById(id)
                .map(userSec -> {
                    userSecRepository.delete(userSec);
                    return true;
                })
                .orElse(false);
    }

    private Set<Role> resolveRoles(Set<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return new HashSet<>();
        }
        if (roleIds.contains(null)) {
            throw new BadRequestException("Role ids must not contain null");
        }
        Set<Role> found = new HashSet<>(roleRepository.findAllById(roleIds));
        if (found.size() < roleIds.size()) {
            Set<Long> foundIds = found.stream().map(Role::getId).collect(Collectors.toSet());
            Set<Long> missing = roleIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toCollection(TreeSet::new));
            throw new BadRequestException("Roles not found: " + missing);
        }
        return found;
    }
}
