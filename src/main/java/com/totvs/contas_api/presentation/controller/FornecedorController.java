package com.totvs.contas_api.presentation.controller;

import com.totvs.contas_api.application.usecase.ListarFornecedorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/fornecedores") // rota base da API
@RequiredArgsConstructor
public class FornecedorController {

    private final ListarFornecedorUseCase listarFornecedorUseCase;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(listarFornecedorUseCase.executar());
    }
}