package com.totvs.contas_api.infrastructure.persistence;

import com.totvs.contas_api.domain.model.ImportacaoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SpringDataImportacaoItemRepository extends JpaRepository<ImportacaoItem, UUID> {

    @Query("SELECT i FROM ImportacaoItem i WHERE i.importacao.id = :id")
    List<ImportacaoItem> buscarPorImportacaoId(@Param("id") UUID id);
}