package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.application.dto.ContaResponse;
import com.totvs.contas_api.application.dto.CriarContaRequest;
import com.totvs.contas_api.domain.exception.BusinessException;
import com.totvs.contas_api.domain.model.Conta;
import com.totvs.contas_api.domain.model.Fornecedor;
import com.totvs.contas_api.domain.repository.ContaRepository;
import com.totvs.contas_api.domain.repository.FornecedorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarContaUseCaseTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private CriarContaUseCase criarContaUseCase;

    @Test
    @DisplayName("Deve criar uma conta com sucesso quando o fornecedor existir")
    void deveCriarContaComSucesso() {
        UUID fornecedorId = UUID.randomUUID();
        CriarContaRequest request = new CriarContaRequest(
                "Energia Elétrica",
                new BigDecimal("150.00"),
                LocalDate.now().plusDays(10),
                fornecedorId
        );

        Fornecedor fornecedorMock = mock(Fornecedor.class);
        when(fornecedorRepository.buscarPorId(fornecedorId)).thenReturn(Optional.of(fornecedorMock));

        ContaResponse response = criarContaUseCase.executar(request);

        assertNotNull(response);
        assertEquals(request.descricao(), response.descricao());
        assertEquals(request.valor(), response.valor());

        verify(contaRepository, times(1)).salvar(any(Conta.class));
        verify(fornecedorRepository, times(1)).buscarPorId(fornecedorId);
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o fornecedor não for encontrado")
    void deveLancarErroQuandoFornecedorInexistente() {
        UUID fornecedorId = UUID.randomUUID();
        CriarContaRequest request = new CriarContaRequest(
                "Internet",
                new BigDecimal("100.00"),
                LocalDate.now(),
                fornecedorId
        );

        when(fornecedorRepository.buscarPorId(fornecedorId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () ->
                criarContaUseCase.executar(request)
        );

        assertEquals("Fornecedor não existe", exception.getMessage());

        verify(contaRepository, never()).salvar(any());
    }
}
