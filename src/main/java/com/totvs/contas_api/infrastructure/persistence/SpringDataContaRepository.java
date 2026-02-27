package com.totvs.contas_api.infrastructure.persistence;

import com.totvs.contas_api.domain.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SpringDataContaRepository extends JpaRepository<Conta, UUID>, JpaSpecificationExecutor<Conta> {
}