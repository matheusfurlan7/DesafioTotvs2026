package com.totvs.contas_api.application.service;

import com.totvs.contas_api.domain.model.Importacao;
import com.totvs.contas_api.domain.model.ImportacaoItem;
import com.totvs.contas_api.domain.repository.ImportacaoItemRepository;
import com.totvs.contas_api.domain.repository.ImportacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImportacaoUseCase {

    private final ImportacaoRepository importacaoRepository;
    private final ImportacaoItemRepository importacaoItemRepository;

    @Transactional
    public void registrarErro(UUID importacaoId, String linha, String mensagemErro) {

        Importacao importacao = importacaoRepository
                .buscarPorId(importacaoId)
                .orElseThrow(() -> new RuntimeException("Importação não encontrada"));

        ImportacaoItem item = new ImportacaoItem(importacao, linha, mensagemErro);

        importacaoItemRepository.salvar(item);

        importacao.incrementarErro();

        importacaoRepository.salvar(importacao);
    }
}