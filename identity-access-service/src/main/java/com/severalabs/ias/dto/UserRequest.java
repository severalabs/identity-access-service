package com.severalabs.ias.dto;

public record UserRequest(
        String email,
        String password
) {
}
