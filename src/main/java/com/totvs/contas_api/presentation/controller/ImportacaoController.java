package com.totvs.contas_api.presentation.controller;

import com.totvs.contas_api.application.usecase.BuscarImportacaoItensPorImportacaoIdUseCase;
import com.totvs.contas_api.application.usecase.CriarImportacaoUseCase;
import com.totvs.contas_api.domain.usecase.BuscarImportacaoUseCase;
import com.totvs.contas_api.presentation.dto.ImportacaoItemResponse;
import com.totvs.contas_api.presentation.dto.ImportacaoDetalheResponse;
import com.totvs.contas_api.presentation.dto.ImportacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/v1/importacoes")
@RequiredArgsConstructor
public class ImportacaoController {

    private final CriarImportacaoUseCase criarImportacaoUseCase;
    private final BuscarImportacaoUseCase buscarImportacaoUseCase;
    private final BuscarImportacaoItensPorImportacaoIdUseCase buscarImportacaoItensPorImportacaoIdUseCase;

    @PostMapping
    public ResponseEntity<ImportacaoResponse> importar(@RequestParam("file") MultipartFile file) throws IOException {

        UUID protocolo = criarImportacaoUseCase.executar(file.getInputStream());

        return ResponseEntity.accepted().body(new ImportacaoResponse(protocolo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImportacaoDetalheResponse> buscar(@PathVariable UUID id) {

        var importacao = buscarImportacaoUseCase.executar(id);
        var itens = buscarImportacaoItensPorImportacaoIdUseCase.executar(id);

        var response = new ImportacaoDetalheResponse(
                importacao.getId(),
                importacao.getSituacao().name(),
                importacao.getTotalLinhas(),
                importacao.getSucesso(),
                importacao.getErro(),
                itens.stream()
                        .map(i -> new ImportacaoItemResponse(
                                i.getLinha(),
                                i.getErro(),
                                i.getDataErro()
                        ))
                        .toList()
        );

        return ResponseEntity.ok(response);
    }
}