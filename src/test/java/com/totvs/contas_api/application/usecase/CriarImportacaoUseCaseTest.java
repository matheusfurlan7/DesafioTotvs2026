package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.infrastructure.messaging.ContaImportacaoProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarImportacaoUseCaseTest {

    @Mock
    private ContaImportacaoProducer producer;

    @InjectMocks
    private CriarImportacaoUseCase criarImportacaoUseCase;

    @Test
    @DisplayName("Deve enviar o arquivo para o producer e retornar o UUID do protocolo")
    void deveEnviarArquivoERetornarProtocolo() {
        InputStream inputStreamMock = new ByteArrayInputStream("header,data".getBytes());
        UUID protocoloEsperado = UUID.randomUUID();

        when(producer.enviar(inputStreamMock)).thenReturn(protocoloEsperado);

        UUID resultado = criarImportacaoUseCase.executar(inputStreamMock);

        assertNotNull(resultado);
        assertEquals(protocoloEsperado, resultado);

        verify(producer, times(1)).enviar(inputStreamMock);
    }
}
