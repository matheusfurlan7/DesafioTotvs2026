package com.totvs.contas_api.application.mapper;

import com.totvs.contas_api.domain.model.Conta;
import com.totvs.contas_api.application.dto.ContaResponse;

public class ContaMapper {

    public static ContaResponse toResponse(Conta conta) {
        return new ContaResponse(
                conta.getId(),
                conta.getDataVencimento(),
                conta.getDataPagamento(),
                conta.getValor(),
                conta.getDescricao(),
                conta.getSituacao().name(),
                conta.getFornecedor().getNome()
        );
    }
}