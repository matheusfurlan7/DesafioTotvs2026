package com.totvs.contas_api.application.service;

import com.totvs.contas_api.domain.model.Importacao;
import com.totvs.contas_api.domain.model.ImportacaoItem;
import com.totvs.contas_api.domain.repository.ImportacaoItemRepository;
import com.totvs.contas_api.domain.repository.ImportacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImportacaoUseCaseTest {

    @Mock
    private ImportacaoRepository importacaoRepository;

    @Mock
    private ImportacaoItemRepository importacaoItemRepository;

    @InjectMocks
    private ImportacaoUseCase importacaoUseCase;

    private final UUID IMPORTACAO_ID = UUID.randomUUID();

    @Test
    @DisplayName("Deve registrar um erro de importação e incrementar o contador de erros da importação pai")
    void deveRegistrarErroComSucesso() {
        String linhaOriginal = "Conta 1,2026-03-01,valor_invalido";
        String mensagemErro = "Formato de valor incorreto";

        Importacao importacaoMock = spy(new Importacao()); // Usamos spy para verificar o incremento interno
        when(importacaoRepository.buscarPorId(IMPORTACAO_ID)).thenReturn(Optional.of(importacaoMock));

        importacaoUseCase.registrarErro(IMPORTACAO_ID, linhaOriginal, mensagemErro);

        ArgumentCaptor<ImportacaoItem> itemCaptor = ArgumentCaptor.forClass(ImportacaoItem.class);
        verify(importacaoItemRepository, times(1)).salvar(itemCaptor.capture());

        ImportacaoItem itemSalvo = itemCaptor.getValue();
        assertEquals(linhaOriginal, itemSalvo.getLinha());
        assertEquals(mensagemErro, itemSalvo.getErro());
        assertEquals(importacaoMock, itemSalvo.getImportacao());

        verify(importacaoMock, times(1)).incrementarErro();
        verify(importacaoRepository, times(1)).salvar(importacaoMock);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar registrar erro em uma importação inexistente")
    void deveLancarExcecaoQuandoImportacaoNaoEncontrada() {
        when(importacaoRepository.buscarPorId(IMPORTACAO_ID)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                importacaoUseCase.registrarErro(IMPORTACAO_ID, "linha", "erro")
        );

        assertEquals("Importação não encontrada", exception.getMessage());
        verify(importacaoItemRepository, never()).salvar(any());
    }
}
