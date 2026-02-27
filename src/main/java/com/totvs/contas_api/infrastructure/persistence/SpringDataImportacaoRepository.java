package com.totvs.contas_api.infrastructure.persistence;

import com.totvs.contas_api.domain.model.Importacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataImportacaoRepository extends JpaRepository<Importacao, UUID> {
}