package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.application.dto.RelatorioResponse;
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
class RelatorioUseCaseTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private RelatorioUseCase relatorioUseCase;

    @Test
    @DisplayName("Deve calcular totais geral, pago e pendente corretamente baseados na página")
    void deveCalcularTotaisCorretamente() {
        Pageable pageable = PageRequest.of(0, 10);

        Fornecedor fornecedorMock = mock(Fornecedor.class);
        when(fornecedorMock.getNome()).thenReturn("Fornecedor Teste");

        Conta contaPaga = mock(Conta.class);
        when(contaPaga.getValor()).thenReturn(new BigDecimal("100.00"));
        when(contaPaga.getSituacao()).thenReturn(Situacao.PAGO);
        when(contaPaga.getFornecedor()).thenReturn(fornecedorMock); // Adicione isso
        when(contaPaga.getDescricao()).thenReturn("Conta 1"); // O Mapper pode precisar disso

        Conta contaPendente = mock(Conta.class);
        when(contaPendente.getValor()).thenReturn(new BigDecimal("200.00"));
        when(contaPendente.getSituacao()).thenReturn(Situacao.PENDENTE);
        when(contaPendente.getFornecedor()).thenReturn(fornecedorMock); // Adicione isso
        when(contaPendente.getDescricao()).thenReturn("Conta 2");

        Page<Conta> pagedResponse = new PageImpl<>(List.of(contaPaga, contaPendente));

        when(contaRepository.buscarComFiltro(any(), any(), any(), eq(pageable)))
                .thenReturn(pagedResponse);

        RelatorioResponse resultado = relatorioUseCase.executar(null, null, null, pageable);

        assertNotNull(resultado);
        assertEquals(new BigDecimal("300.00"), resultado.totalGeral());
        assertEquals(new BigDecimal("100.00"), resultado.totalPago());
        assertEquals(new BigDecimal("200.00"), resultado.totalPendente());
    }


    @Test
    @DisplayName("Deve retornar totais zerados quando a página estiver vazia")
    void deveRetornarTotaisZeradosParaPaginaVazia() {
        Pageable pageable = PageRequest.of(0, 10);
        when(contaRepository.buscarComFiltro(any(), any(), any(), any()))
                .thenReturn(Page.empty());

        RelatorioResponse resultado = relatorioUseCase.executar(null, null, null, pageable);

        assertEquals(BigDecimal.ZERO, resultado.totalGeral());
        assertEquals(BigDecimal.ZERO, resultado.totalPago());
        assertEquals(BigDecimal.ZERO, resultado.totalPendente());
        assertTrue(resultado.contas().isEmpty());
    }
}
