package com.totvs.contas_api.infrastructure.persistence;

import com.totvs.contas_api.domain.model.Importacao;
import com.totvs.contas_api.domain.repository.ImportacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ImportacaoRepositoryImpl implements ImportacaoRepository {

    private final SpringDataImportacaoRepository springRepository;

    @Override
    public Importacao salvar(Importacao importacao) {
        return springRepository.save(importacao);
    }

    @Override
    public Optional<Importacao> buscarPorId(UUID id) {
        return springRepository.findById(id);
    }
}