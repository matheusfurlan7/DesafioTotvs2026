package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.infrastructure.messaging.ContaImportacaoProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CriarImportacaoUseCase {

    private final ContaImportacaoProducer producer;

    public UUID executar(InputStream fileInputStream) {

        return producer.enviar(fileInputStream);
    }
}