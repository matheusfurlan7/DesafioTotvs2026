package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.domain.model.ImportacaoItem;
import com.totvs.contas_api.domain.repository.ImportacaoItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarImportacaoItensPorImportacaoIdUseCaseTest {

    @Mock
    private ImportacaoItemRepository importacaoItemRepository;

    @InjectMocks
    private BuscarImportacaoItensPorImportacaoIdUseCase useCase;

    @Test
    @DisplayName("Deve retornar lista de itens quando encontrar registros para o ID da importação")
    void deveRetornarListaDeItensComSucesso() {
        UUID importacaoId = UUID.randomUUID();
        ImportacaoItem item1 = new ImportacaoItem();
        ImportacaoItem item2 = new ImportacaoItem();

        List<ImportacaoItem> itensEsperados = List.of(item1, item2);

        when(importacaoItemRepository.buscarPorImportacaoId(importacaoId))
                .thenReturn(itensEsperados);

        List<ImportacaoItem> resultado = useCase.executar(importacaoId);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(itensEsperados, resultado);

        verify(importacaoItemRepository, times(1)).buscarPorImportacaoId(importacaoId);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver itens para o ID informado")
    void deveRetornarListaVaziaQuandoNaoHouverItens() {
        UUID importacaoId = UUID.randomUUID();
        when(importacaoItemRepository.buscarPorImportacaoId(importacaoId))
                .thenReturn(List.of());

        List<ImportacaoItem> resultado = useCase.executar(importacaoId);

        assertTrue(resultado.isEmpty());
        verify(importacaoItemRepository, times(1)).buscarPorImportacaoId(importacaoId);
    }
}
