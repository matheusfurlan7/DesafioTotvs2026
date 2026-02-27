package com.totvs.contas_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    public Fornecedor(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do fornecedor é obrigatório");
        }
        this.nome = nome;
    }
}