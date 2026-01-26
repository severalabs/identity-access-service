package com.severalabs.ias.dto;

public record SignUpResponse(
        Long id,
        String email,
        String role
) {
}
