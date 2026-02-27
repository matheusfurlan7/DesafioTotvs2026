package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.domain.model.ImportacaoItem;
import com.totvs.contas_api.domain.repository.ImportacaoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarImportacaoItensPorImportacaoIdUseCase {

    private final ImportacaoItemRepository importacaoItemRepository;

    public List<ImportacaoItem> executar(UUID id) {
        List<ImportacaoItem> itens = importacaoItemRepository.buscarPorImportacaoId(id);

        return itens;
    }
}