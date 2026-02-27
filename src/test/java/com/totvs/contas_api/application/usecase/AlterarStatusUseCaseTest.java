package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.domain.exception.BusinessException;
import com.totvs.contas_api.domain.model.Conta;
import com.totvs.contas_api.domain.repository.ContaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlterarStatusUseCaseTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private Conta conta;

    @InjectMocks
    private AlterarStatusUseCase alterarStatusUseCase;

    private final UUID CONTA_ID = UUID.randomUUID();

    @Test
    @DisplayName("Deve registrar pagamento com sucesso")
    void devePagarComSucesso() {
        LocalDate dataPagamento = LocalDate.now();
        when(contaRepository.buscarPorId(CONTA_ID)).thenReturn(Optional.of(conta));

        alterarStatusUseCase.pagar(CONTA_ID, dataPagamento);

        verify(conta, times(1)).pagar(dataPagamento);
        verify(contaRepository, times(1)).salvar(conta);
    }

    @Test
    @DisplayName("Deve cancelar conta com sucesso")
    void deveCancelarComSucesso() {
        when(contaRepository.buscarPorId(CONTA_ID)).thenReturn(Optional.of(conta));

        alterarStatusUseCase.cancelar(CONTA_ID);

        verify(conta, times(1)).cancelar();
        verify(contaRepository, times(1)).salvar(conta);
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando conta não existir no pagamento")
    void deveLancarErroAoPagarContaInexistente() {
        when(contaRepository.buscarPorId(CONTA_ID)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () ->
                alterarStatusUseCase.pagar(CONTA_ID, LocalDate.now())
        );

        verify(contaRepository, never()).salvar(any());
    }
}
