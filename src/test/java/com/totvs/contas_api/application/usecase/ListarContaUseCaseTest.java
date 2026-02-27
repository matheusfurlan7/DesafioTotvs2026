package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.application.dto.ContaResponse;
import com.totvs.contas_api.domain.model.Conta;
import com.totvs.contas_api.domain.model.Fornecedor;
import com.totvs.contas_api.domain.model.Situacao;
import com.totvs.contas_api.domain.repository.ContaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarContaUseCaseTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ListarContaUseCase listarContaUseCase;

    @Test
    @DisplayName("Deve retornar uma página de ContaResponse ao listar com filtros")
    void deveRetornarPaginaDeContasComSucesso() {
        String descricao = "Aluguel";
        LocalDate inicio = LocalDate.now().minusDays(30);
        LocalDate fim = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 10);

        Fornecedor fornecedorMock = mock(Fornecedor.class);
        when(fornecedorMock.getNome()).thenReturn("Fornecedor Teste");

        Conta contaMock = mock(Conta.class);
        when(contaMock.getDescricao()).thenReturn("Aluguel Escritório");
        when(contaMock.getValor()).thenReturn(new BigDecimal("1000.00"));
        when(contaMock.getSituacao()).thenReturn(Situacao.PENDENTE);
        when(contaMock.getFornecedor()).thenReturn(fornecedorMock);
        when(contaMock.getDataVencimento()).thenReturn(LocalDate.now());

        Page<Conta> pagedResponse = new PageImpl<>(List.of(contaMock));

        when(contaRepository.buscarComFiltro(descricao, inicio, fim, pageable))
                .thenReturn(pagedResponse);

        Page<ContaResponse> resultado = listarContaUseCase.executar(descricao, inicio, fim, pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Aluguel Escritório", resultado.getContent().get(0).descricao());

        verify(contaRepository, times(1)).buscarComFiltro(descricao, inicio, fim, pageable);
    }


    @Test
    @DisplayName("Deve retornar página vazia quando nenhum registro for encontrado")
    void deveRetornarPaginaVazia() {
        Pageable pageable = PageRequest.of(0, 10);
        when(contaRepository.buscarComFiltro(any(), any(), any(), eq(pageable)))
                .thenReturn(Page.empty());

        Page<ContaResponse> resultado = listarContaUseCase.executar(null, null, null, pageable);

        assertTrue(resultado.isEmpty());
        assertEquals(0, resultado.getTotalElements());
        verify(contaRepository, times(1)).buscarComFiltro(null, null, null, pageable);
    }
}
