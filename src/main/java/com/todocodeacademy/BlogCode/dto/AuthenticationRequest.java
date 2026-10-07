package com.todocodeacademy.BlogCode.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username","password"})
public record AuthenticationRequest (String username, String password) {
}
