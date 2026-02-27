package com.totvs.contas_api.domain.usecase;

import com.totvs.contas_api.domain.model.Importacao;
import com.totvs.contas_api.domain.repository.ImportacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarImportacaoUseCaseTest {

    @Mock
    private ImportacaoRepository importacaoRepository;

    @InjectMocks
    private BuscarImportacaoUseCase  buscarImportacaoUseCase;

    private final UUID IMPORTACAO_ID = UUID.randomUUID();

    @Test
    @DisplayName("Deve retornar uma importação quando o ID existir")
    void deveRetornarImportacaoQuandoIdExistir() {
        Importacao importacaoEsperada = new Importacao();

        when(importacaoRepository.buscarPorId(IMPORTACAO_ID))
                .thenReturn(Optional.of(importacaoEsperada));

        Importacao resultado = buscarImportacaoUseCase.executar(IMPORTACAO_ID);

        assertNotNull(resultado);
        assertEquals(importacaoEsperada, resultado);
        verify(importacaoRepository, times(1)).buscarPorId(IMPORTACAO_ID);
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando a importação não for encontrada")
    void deveLancarExcecaoQuandoImportacaoNaoEncontrada() {
        when(importacaoRepository.buscarPorId(IMPORTACAO_ID))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            buscarImportacaoUseCase.executar(IMPORTACAO_ID);
        });

        assertEquals("Importação não encontrada", exception.getMessage());
        verify(importacaoRepository, times(1)).buscarPorId(IMPORTACAO_ID);
    }
}