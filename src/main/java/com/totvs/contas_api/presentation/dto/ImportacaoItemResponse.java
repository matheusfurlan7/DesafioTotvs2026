package com.totvs.contas_api.presentation.dto;

import java.time.LocalDateTime;

public record ImportacaoItemResponse(
        String linha,
        String erro,
        LocalDateTime dataErro
) {}