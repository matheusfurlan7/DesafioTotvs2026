package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.application.dto.ContaResponse;
import com.totvs.contas_api.application.dto.CriarContaRequest;
import com.totvs.contas_api.application.mapper.ContaMapper;
import com.totvs.contas_api.domain.exception.BusinessException;
import com.totvs.contas_api.domain.model.Conta;
import com.totvs.contas_api.domain.model.Fornecedor;
import com.totvs.contas_api.domain.repository.ContaRepository;
import com.totvs.contas_api.domain.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarContaUseCase {

    private final ContaRepository contaRepository;
    private final FornecedorRepository fornecedorRepository;

    @Transactional
    public ContaResponse executar(CriarContaRequest request) {

        Fornecedor fornecedor = fornecedorRepository.buscarPorId(request.fornecedorId())
                .orElseThrow(() -> new BusinessException("Fornecedor não existe"));

        Conta conta = new Conta(
                request.dataVencimento(),
                request.valor(),
                request.descricao(),
                fornecedor
        );

        contaRepository.salvar(conta);

        return ContaMapper.toResponse(conta);
    }
}