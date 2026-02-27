package com.totvs.contas_api.domain.repository;

import com.totvs.contas_api.domain.model.ImportacaoItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImportacaoItemRepository {

    ImportacaoItem salvar(ImportacaoItem importacaoItem);

    Optional<ImportacaoItem> buscarPorId(UUID id);

    List<ImportacaoItem> buscarPorImportacaoId(UUID id);
}
