package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.application.dto.ContaRelatorioItem;
import com.totvs.contas_api.application.dto.FornecedorRelatorioItem;
import com.totvs.contas_api.domain.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarFornecedorUseCase {

    private final FornecedorRepository fornecedorRepository;

    @Transactional(readOnly = true)
    public List<FornecedorRelatorioItem> executar() {

        return fornecedorRepository.buscarTodos()
                .stream()
                .map(fornecedor -> new FornecedorRelatorioItem(
                        fornecedor.getId(),
                        fornecedor.getNome()
                ))
                .toList();
    }
}