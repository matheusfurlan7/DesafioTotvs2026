package com.totvs.contas_api.infrastructure.persistence;

import com.totvs.contas_api.domain.model.Fornecedor;
import com.totvs.contas_api.domain.model.ImportacaoItem;
import com.totvs.contas_api.domain.repository.ImportacaoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ImportacaoItemRepositoryImpl implements ImportacaoItemRepository {

    private final SpringDataImportacaoItemRepository springRepository;

    @Override
    public ImportacaoItem salvar(ImportacaoItem importacaoItem) {
        return springRepository.save(importacaoItem);
    }

    @Override
    public Optional<ImportacaoItem> buscarPorId(UUID id) {
        return springRepository.findById(id);
    }

    @Override
    public List<ImportacaoItem> buscarPorImportacaoId(UUID id) {
        return springRepository.buscarPorImportacaoId(id);
    }
}