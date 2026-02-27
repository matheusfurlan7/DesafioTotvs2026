package com.totvs.contas_api.infrastructure.persistence;

import com.totvs.contas_api.domain.model.Fornecedor;
import com.totvs.contas_api.domain.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FornecedorRepositoryImpl implements FornecedorRepository {

    private final SpringDataFornecedorRepository jpaRepository;

    @Override
    public Optional<Fornecedor> buscarPorId(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Fornecedor> buscarTodos() {
        return jpaRepository.findAll();
    }
}