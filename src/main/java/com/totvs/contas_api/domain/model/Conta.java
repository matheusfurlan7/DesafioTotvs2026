package com.totvs.contas_api.domain.model;

import com.totvs.contas_api.domain.exception.DomainException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

    @Column(nullable = false)
    private BigDecimal valor;

    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Situacao situacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    public Conta(LocalDate dataVencimento,
                 BigDecimal valor,
                 String descricao,
                 Fornecedor fornecedor) {

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Valor inválido");
        }

        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.descricao = descricao;
        this.fornecedor = fornecedor;
        this.situacao = Situacao.PENDENTE;
    }

    public void pagar(LocalDate dataPagamento) {
        if (this.situacao == Situacao.PAGO) {
            throw new DomainException("Conta já paga");
        }

        if (dataPagamento.isBefore(dataVencimento)) {
            throw new DomainException("Data de pagamento inválida");
        }

        this.situacao = Situacao.PAGO;
        this.dataPagamento = dataPagamento;
    }

    public void cancelar() {
        if (this.situacao == Situacao.PAGO) {
            throw new DomainException("Conta paga não pode ser cancelada");
        }

        if (this.situacao == Situacao.CANCELADO) {
            throw new DomainException("Conta já cancelada");
        }

        this.situacao = Situacao.CANCELADO;
    }
}