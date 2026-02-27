package com.totvs.contas_api.infrastructure.messaging;

import com.totvs.contas_api.domain.exception.BusinessException;
import com.totvs.contas_api.domain.model.Importacao;
import com.totvs.contas_api.domain.repository.ImportacaoRepository;
import com.totvs.contas_api.infrastructure.messaging.message.ImportacaoMensagem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static com.totvs.contas_api.domain.model.SituacaoImportacao.PROCESSANDO;

@Component
@RequiredArgsConstructor
public class ContaImportacaoProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ImportacaoRepository importacaoRepository;

    @Transactional
    public UUID enviar(InputStream inputStream) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            List<String> linhasValidas = reader.lines()
                .skip(1)
                .filter(l -> !l.isBlank())
                .toList();

            if (linhasValidas.isEmpty()) {
                throw new BusinessException("Arquivo informado é inválido.");
            }

            Importacao importacao = new Importacao();
            importacao.setTotalLinhas(linhasValidas.size());
            importacaoRepository.salvar(importacao);

            for (String linha : linhasValidas) {
                ImportacaoMensagem mensagem = new ImportacaoMensagem(importacao.getId(), linha);

                rabbitTemplate.convertAndSend(RabbitConfig.IMPORTACAO_QUEUE, mensagem);
            }

            return importacao.getId();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo", e);
        }
    }
}