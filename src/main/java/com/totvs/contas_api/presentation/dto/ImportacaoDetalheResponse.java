package com.totvs.contas_api.presentation.dto;

import java.util.List;
import java.util.UUID;

public record ImportacaoDetalheResponse(
        UUID id,
        String situacao,
        Integer totalLinhas,
        Integer sucesso,
        Integer erro,
        List<ImportacaoItemResponse> erros
) {}