package com.severalabs.ias.dto;

public record LoginRequest(
        String email,
        String password
) {
}
