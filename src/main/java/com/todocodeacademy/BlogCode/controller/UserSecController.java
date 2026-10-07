package com.todocodeacademy.BlogCode.controller;

import com.todocodeacademy.BlogCode.dto.UserSecRequestDTO;
import com.todocodeacademy.BlogCode.dto.UserSecResponseDTO;
import com.todocodeacademy.BlogCode.service.IUserSecService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserSecController {

    private final IUserSecService userSecService;

    public UserSecController(IUserSecService userSecService) {
        this.userSecService = userSecService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserSecResponseDTO> createUser(@RequestBody UserSecRequestDTO requestDTO) {
        UserSecResponseDTO created = userSecService.save(requestDTO);
        return ResponseEntity.created(URI.create("/api/users/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserSecResponseDTO> getUserById(@PathVariable Long id) {
        return userSecService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserSecResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userSecService.findAll());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userSecService.deleteById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserSecResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserSecRequestDTO requestDTO) {
        return userSecService.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
