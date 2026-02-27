package com.totvs.contas_api.application.dto;

import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public record RelatorioResponse(
        Page<ContaResponse> contas,
        BigDecimal totalGeral,
        BigDecimal totalPago,
        BigDecimal totalPendente
) {}