package com.totvs.contas_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "importacao_itens")
@Getter
public class ImportacaoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "importacao_id", nullable = false)
    private Importacao importacao;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String linha;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String erro;

    @Column(nullable = false)
    private LocalDateTime dataErro;

    public ImportacaoItem() {}

    public ImportacaoItem(Importacao importacao, String linha, String erro) {
        this.importacao = importacao;
        this.linha = linha;
        this.erro = erro;
        this.dataErro = LocalDateTime.now();
    }
}