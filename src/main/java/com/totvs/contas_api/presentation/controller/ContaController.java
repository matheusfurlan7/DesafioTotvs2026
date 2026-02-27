package com.totvs.contas_api.presentation.controller;

import com.totvs.contas_api.application.dto.ContaResponse;
import com.totvs.contas_api.application.dto.CriarContaRequest;
import com.totvs.contas_api.application.dto.RelatorioResponse;
import com.totvs.contas_api.application.usecase.AlterarStatusUseCase;
import com.totvs.contas_api.application.usecase.CriarContaUseCase;
import com.totvs.contas_api.application.usecase.ListarContaUseCase;
import com.totvs.contas_api.application.usecase.RelatorioUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/v1/contas") // rota base da API
@RequiredArgsConstructor
public class ContaController {

    private final CriarContaUseCase criarContaUseCase;
    private final ListarContaUseCase listarContaUseCase;
    private final AlterarStatusUseCase alterarStatusUseCase;
    private final RelatorioUseCase relatorioUseCase;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid CriarContaRequest request) {
        return ResponseEntity.ok(criarContaUseCase.executar(request));
    }

    @GetMapping
    public Page<ContaResponse> listar(
            @RequestParam(required = false) String descricao,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataInicio,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataFim,

            Pageable pageable) {

        return listarContaUseCase.executar(descricao, dataInicio, dataFim, pageable);
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<?> pagar(
            @PathVariable UUID id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPagamento
    ) {
        alterarStatusUseCase.pagar(id, dataPagamento);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(
            @PathVariable UUID id
    ) {
        alterarStatusUseCase.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio")
    public RelatorioResponse relatorio(
            @RequestParam(required = false) String descricao,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataInicio,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataFim,

            Pageable pageable) {

        return relatorioUseCase.executar(descricao, dataInicio, dataFim, pageable);
    }
}