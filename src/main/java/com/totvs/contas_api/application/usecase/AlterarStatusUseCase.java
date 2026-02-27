package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.domain.exception.BusinessException;
import com.totvs.contas_api.domain.model.Conta;
import com.totvs.contas_api.domain.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlterarStatusUseCase {

    private final ContaRepository contaRepository;

    @Transactional
    public void pagar(UUID contaId, LocalDate dataPagamento) {

        Conta conta = contaRepository.buscarPorId(contaId)
                .orElseThrow(() ->
                        new BusinessException("Conta não encontrada"));

        conta.pagar(dataPagamento);

        contaRepository.salvar(conta);
    }

    @Transactional
    public void cancelar(UUID contaId) {

        Conta conta = contaRepository.buscarPorId(contaId)
                .orElseThrow(() ->
                        new BusinessException("Conta não encontrada"));

        conta.cancelar();

        contaRepository.salvar(conta);
    }
}