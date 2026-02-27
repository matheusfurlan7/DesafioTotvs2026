package com.totvs.contas_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.totvs.contas_api.domain.model.SituacaoImportacao.PROCESSANDO;

@Entity
@Table(name = "importacoes")
public class Importacao {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Getter
    @Setter
    @Column(nullable = false)
    private Integer totalLinhas;

    @Getter
    @Column(nullable = false)
    private Integer processadas;

    @Getter
    @Column(nullable = false)
    private Integer sucesso;

    @Getter
    @Column(nullable = false)
    private Integer erro;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private SituacaoImportacao situacao;

    @Getter
    private LocalDateTime dataInicio;

    @Getter
    @Setter
    private LocalDateTime dataFim;

    @Version
    private Long versao;

    public Importacao() {
        this.totalLinhas = 0;
        this.processadas = 0;
        this.sucesso = 0;
        this.erro = 0;
        this.situacao = PROCESSANDO;
        this.dataInicio = LocalDateTime.now();
    }

    public void incrementarSucesso() {
        this.sucesso++;
        this.processadas++;
    }

    public void incrementarErro() {
        this.erro++;
        this.processadas++;
    }

    public boolean terminou() {
        return this.processadas.equals(this.totalLinhas);
    }

    public void finalizar() {
        this.situacao = SituacaoImportacao.CONCLUIDO;
        this.dataFim = LocalDateTime.now();
    }
}