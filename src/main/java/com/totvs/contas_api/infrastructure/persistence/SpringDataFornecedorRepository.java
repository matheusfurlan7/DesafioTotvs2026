package com.totvs.contas_api.infrastructure.persistence;

import com.totvs.contas_api.domain.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataFornecedorRepository extends JpaRepository<Fornecedor, UUID> {

    Optional<Fornecedor> findByNome(String nome);
}