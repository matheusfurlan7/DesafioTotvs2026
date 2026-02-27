package com.totvs.contas_api.domain.repository;

import com.totvs.contas_api.domain.model.Importacao;

import java.util.Optional;
import java.util.UUID;

public interface ImportacaoRepository {

    Importacao salvar(Importacao importacao);

    Optional<Importacao> buscarPorId(UUID id);
}