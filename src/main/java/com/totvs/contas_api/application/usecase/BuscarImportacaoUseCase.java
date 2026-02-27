package com.totvs.contas_api.domain.usecase;

import com.totvs.contas_api.domain.model.Importacao;
import com.totvs.contas_api.domain.repository.ImportacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarImportacaoUseCase {

    private final ImportacaoRepository importacaoRepository;

    public Importacao executar(UUID id) {
        return importacaoRepository
                .buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Importação não encontrada"));
    }
}