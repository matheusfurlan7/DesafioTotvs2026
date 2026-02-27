package com.totvs.contas_api.infrastructure.messaging;

import com.totvs.contas_api.application.usecase.ImportarContaUseCase;
import com.totvs.contas_api.domain.model.Importacao;
import com.totvs.contas_api.domain.model.ImportacaoItem;
import com.totvs.contas_api.domain.repository.ImportacaoItemRepository;
import com.totvs.contas_api.domain.repository.ImportacaoRepository;
import com.totvs.contas_api.infrastructure.messaging.message.ImportacaoMensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContaImportacaoListener {

    private final ImportarContaUseCase importarContaUseCase;
    private final ImportacaoRepository importacaoRepository;
    private final ImportacaoItemRepository importacaoItemRepository;

    @RabbitListener(queues = RabbitConfig.IMPORTACAO_QUEUE)
    public void processar(ImportacaoMensagem mensagem) {

        Importacao importacao = importacaoRepository.buscarPorId(mensagem.getImportacaoId()).orElseThrow();

        try {
            importarContaUseCase.processarLinha(mensagem.getLinha());
            importacao.incrementarSucesso();
        } catch (Exception e) {

            log.error("Erro ao processar linha da importacao {}", mensagem.getImportacaoId(), e);

            importacao.incrementarErro();

            ImportacaoItem item = new ImportacaoItem(importacao, mensagem.getLinha(), e.getMessage());

            importacaoItemRepository.salvar(item);
        }

        if (importacao.terminou()) {
            importacao.finalizar();
        }

        importacaoRepository.salvar(importacao);
    }
}