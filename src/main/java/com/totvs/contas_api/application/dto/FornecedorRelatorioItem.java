package com.totvs.contas_api.application.dto;

import java.util.UUID;

public record FornecedorRelatorioItem(
        UUID id,
        String nome
) {}