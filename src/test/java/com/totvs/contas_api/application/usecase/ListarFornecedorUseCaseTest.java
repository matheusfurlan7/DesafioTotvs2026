package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.application.dto.FornecedorRelatorioItem;
import com.totvs.contas_api.domain.model.Fornecedor;
import com.totvs.contas_api.domain.repository.FornecedorRepository;
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
class ListarFornecedorUseCaseTest {

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private ListarFornecedorUseCase listarFornecedorUseCase;

    @Test
    @DisplayName("Deve retornar uma lista de FornecedorRelatorioItem mapeada corretamente")
    void deveRetornarListaDeFornecedoresComSucesso() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Fornecedor f1 = mock(Fornecedor.class);
        when(f1.getId()).thenReturn(id1);
        when(f1.getNome()).thenReturn("Fornecedor A");

        Fornecedor f2 = mock(Fornecedor.class);
        when(f2.getId()).thenReturn(id2);
        when(f2.getNome()).thenReturn("Fornecedor B");

        when(fornecedorRepository.buscarTodos()).thenReturn(List.of(f1, f2));

        List<FornecedorRelatorioItem> resultado = listarFornecedorUseCase.executar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        assertEquals(id1, resultado.get(0).id());
        assertEquals("Fornecedor A", resultado.get(0).nome());

        assertEquals(id2, resultado.get(1).id());
        assertEquals("Fornecedor B", resultado.get(1).nome());

        verify(fornecedorRepository, times(1)).buscarTodos();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver fornecedores cadastrados")
    void deveRetornarListaVaziaQuandoNaoHouverDados() {
        when(fornecedorRepository.buscarTodos()).thenReturn(List.of());

        List<FornecedorRelatorioItem> resultado = listarFornecedorUseCase.executar();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(fornecedorRepository, times(1)).buscarTodos();
    }
}
