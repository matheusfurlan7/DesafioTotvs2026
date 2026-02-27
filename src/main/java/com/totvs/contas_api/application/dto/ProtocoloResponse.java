package com.totvs.contas_api.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProtocoloResponse(
        UUID protocolo,
        String status,
        LocalDateTime dataCriacao,
        Integer totalRegistros,
        Integer registrosProcessados,
        Integer registrosComErro
) {}