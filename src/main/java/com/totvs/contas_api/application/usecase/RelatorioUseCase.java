package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.application.dto.RelatorioResponse;
import com.totvs.contas_api.application.mapper.ContaMapper;
import com.totvs.contas_api.domain.model.Situacao;
import com.totvs.contas_api.domain.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RelatorioUseCase {

    private final ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public RelatorioResponse executar(
            String descricao,
            LocalDate inicio,
            LocalDate fim,
            Pageable pageable) {

        var contas = contaRepository.buscarComFiltro(descricao,
                inicio,
                fim,
                pageable);

        BigDecimal totalGeral = contas.stream()
                .map(c -> c.getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPago = contas.stream()
                .filter(c -> c.getSituacao() == Situacao.PAGO)
                .map(c -> c.getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPendente = contas.stream()
                .filter(c -> c.getSituacao() == Situacao.PENDENTE)
                .map(c -> c.getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var itens = contas.map(conta -> ContaMapper.toResponse(conta));

        return new RelatorioResponse(
                itens,
                totalGeral,
                totalPago,
                totalPendente
        );
    }
}