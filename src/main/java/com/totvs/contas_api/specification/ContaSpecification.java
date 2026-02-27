package com.totvs.contas_api.specification;

import com.totvs.contas_api.domain.model.Conta;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ContaSpecification {

    public static Specification<Conta> descricaoLike(String descricao) {
        return (root, query, cb) -> {

            if (descricao == null || descricao.isBlank()) {
                return null;
            }

            return cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
        };
    }

    public static Specification<Conta> dataVencimentoEntre(LocalDate inicio, LocalDate fim) {

        return (root, query, cb) -> {
            if (inicio == null && fim == null) return null;

            if (inicio != null && fim != null) return cb.between(root.get("dataVencimento"), inicio, fim);

            if (inicio != null) return cb.greaterThanOrEqualTo(root.get("dataVencimento"), inicio);

            return cb.lessThanOrEqualTo(root.get("dataVencimento"), fim);
        };
    }
}