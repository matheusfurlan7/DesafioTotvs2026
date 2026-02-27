package com.totvs.contas_api.infrastructure.security.dto;

public record LoginRequest(
        String username,
        String password
) {}