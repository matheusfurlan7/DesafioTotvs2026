package com.totvs.contas_api.domain.repository;

import com.totvs.contas_api.domain.model.Fornecedor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FornecedorRepository {

    Optional<Fornecedor> buscarPorId(UUID id);

    List<Fornecedor> buscarTodos();
}