package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.domain.exception.BusinessException;
import com.totvs.contas_api.domain.model.Conta;
import com.totvs.contas_api.domain.model.Fornecedor;
import com.totvs.contas_api.domain.repository.ContaRepository;
import com.totvs.contas_api.domain.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImportarContaUseCase {

    private final FornecedorRepository fornecedorRepository;
    private final ContaRepository contaRepository;

    public void processarLinha(String linha) {

        String[] campos = linha.split(",");

        if (campos.length != 4) {
            throw new BusinessException("Linha inválida: quantidade de colunas incorreta");
        }

        String descricao = campos[0].trim();

        if (descricao.isBlank()) {
            throw new BusinessException("Descrição obrigatória");
        }

        LocalDate dataVencimento;

        try {
            dataVencimento = LocalDate.parse(campos[1].trim());
        } catch (DateTimeParseException e) {
            throw new BusinessException("Data de vencimento inválida");
        }

        BigDecimal valor;

        try {
            valor = new BigDecimal(campos[2].trim());

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                throw new BusinessException("Valor deve ser maior que zero");
            }

        } catch (NumberFormatException e) {
            throw new BusinessException("Valor inválido");
        }

        UUID fornecedorId;

        try {
            fornecedorId = UUID.fromString(campos[3].trim());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Fornecedor inválido");
        }

        Fornecedor fornecedor = fornecedorRepository
                .buscarPorId(fornecedorId)
                .orElseThrow(() ->
                        new BusinessException("Fornecedor não encontrado"));

        Conta conta = new Conta(dataVencimento, valor, descricao, fornecedor);

        contaRepository.salvar(conta);
    }
}