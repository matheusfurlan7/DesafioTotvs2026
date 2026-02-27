package com.totvs.contas_api.infrastructure.persistence;

import com.totvs.contas_api.domain.model.Conta;
import com.totvs.contas_api.domain.repository.ContaRepository;
import com.totvs.contas_api.specification.ContaSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ContaRepositoryImpl implements ContaRepository {

    private final SpringDataContaRepository jpaRepository;

    @Override
    public Conta salvar(Conta conta) {
        return jpaRepository.save(conta);
    }

    @Override
    public Optional<Conta> buscarPorId(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Conta> buscarTodas() {
        return jpaRepository.findAll();
    }

    @Override
    public Page<Conta> buscarComFiltro(
            String descricao,
            LocalDate inicio,
            LocalDate fim,
            Pageable pageable) {

        var spec = Specification
                .where(ContaSpecification.descricaoLike(descricao))
                .and(ContaSpecification.dataVencimentoEntre(inicio, fim));

        return jpaRepository.findAll(spec, pageable);
    }
}