package br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.consumer;

import br.com.bali.code.healthcareapiusuarios.Usuario.domain.service.NotificationService;
import br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.config.RabbitMQConfig;
import br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.dto.TriagemClassificadaPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TriagemClassificadaConsumer {

    private final NotificationService notificationService;

    public TriagemClassificadaConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Consome eventos triagem.classificada.
     * Sem try/catch genérico: exceções propagam para DLQ via x-dead-letter-exchange.
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_TRIAGEM_CLASSIFICADA)
    public void consume(TriagemClassificadaPayload payload) {
        log.info("[triagem.classificada] Recebida: medicoId={} prioridade={}",
                payload.medicoId(), payload.prioridade());

        notificationService.notifyDoctor(payload);

        log.info("[triagem.classificada] Notificação enviada para medicoId={}", payload.medicoId());
    }
}
