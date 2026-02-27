package com.totvs.contas_api.application.usecase;

import com.totvs.contas_api.application.dto.ContaResponse;
import com.totvs.contas_api.application.mapper.ContaMapper;
import com.totvs.contas_api.domain.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ListarContaUseCase {

    private final ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public Page<ContaResponse> executar(
            String descricao,
            LocalDate inicio,
            LocalDate fim,
            Pageable pageable) {

        return contaRepository.buscarComFiltro(descricao,
                        inicio,
                        fim,
                        pageable)
                .map(conta -> ContaMapper.toResponse(conta));
    }
}