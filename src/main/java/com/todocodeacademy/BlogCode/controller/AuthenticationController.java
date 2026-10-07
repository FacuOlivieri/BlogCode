package com.todocodeacademy.BlogCode.controller;

import com.todocodeacademy.BlogCode.dto.AuthenticationRequest;
import com.todocodeacademy.BlogCode.dto.AuthenticationResponseDTO;
import com.todocodeacademy.BlogCode.service.UserDetailServiceImp;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("permitAll()")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImp userDetailService;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(this.userDetailService.login(authenticationRequest), HttpStatus.OK);
    }
}
