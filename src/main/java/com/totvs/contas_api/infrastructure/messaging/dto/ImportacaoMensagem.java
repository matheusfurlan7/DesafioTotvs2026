package com.totvs.contas_api.infrastructure.messaging.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportacaoMensagem {
    private UUID importacaoId;
    private String linha;
}