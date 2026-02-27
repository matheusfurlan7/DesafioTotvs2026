package com.totvs.contas_api.domain.repository;

import com.totvs.contas_api.domain.model.Conta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContaRepository {

    Conta salvar(Conta conta);

    Optional<Conta> buscarPorId(UUID id);

    List<Conta> buscarTodas();

    Page<Conta> buscarComFiltro(
            String descricao,
            LocalDate dataInicio,
            LocalDate dataFim,
            Pageable pageable
    );
}