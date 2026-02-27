package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.domain.exception.BusinessException;
import com.totvs.contas_api.domain.model.Conta;
import com.totvs.contas_api.domain.model.Fornecedor;
import com.totvs.contas_api.domain.repository.ContaRepository;
import com.totvs.contas_api.domain.repository.FornecedorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImportarContaUseCaseTest {

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ImportarContaUseCase importarContaUseCase;

    private final UUID FORNECEDOR_ID = UUID.randomUUID();

    @Test
    @DisplayName("Deve processar linha válida e salvar a conta")
    void deveProcessarLinhaValida() {
        String linha = String.format("Conta Luz,2026-03-01,150.50,%s", FORNECEDOR_ID);
        Fornecedor fornecedor = mock(Fornecedor.class);

        when(fornecedorRepository.buscarPorId(FORNECEDOR_ID)).thenReturn(Optional.of(fornecedor));

        assertDoesNotThrow(() -> importarContaUseCase.processarLinha(linha));

        verify(fornecedorRepository, times(1)).buscarPorId(FORNECEDOR_ID);
        verify(contaRepository, times(1)).salvar(any(Conta.class));
    }

    @ParameterizedTest
    @DisplayName("Deve lançar exceção para linhas com formato ou dados inválidos")
    @ValueSource(strings = {
            "Descricao,2026-01-01,100.00",
            " ,2026-01-01,100.00,uuid",
            "Conta,data-errada,100.00,uuid",
            "Conta,2026-01-01,abc,uuid",
            "Conta,2026-01-01,-50.00,uuid",
            "Conta,2026-01-01,100.00,id-ruim"
    })
    void deveLancarErroParaDadosInvalidos(String linhaInvalida) {
        assertThrows(BusinessException.class, () ->
                importarContaUseCase.processarLinha(linhaInvalida)
        );

        verify(contaRepository, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando o fornecedor da linha não existe no banco")
    void deveLancarErroFornecedorNaoEncontrado() {
        String linha = String.format("Conta,2026-03-01,100.00,%s", FORNECEDOR_ID);
        when(fornecedorRepository.buscarPorId(FORNECEDOR_ID)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                importarContaUseCase.processarLinha(linha)
        );

        assertEquals("Fornecedor não encontrado", ex.getMessage());
        verify(contaRepository, never()).salvar(any());
    }
}
