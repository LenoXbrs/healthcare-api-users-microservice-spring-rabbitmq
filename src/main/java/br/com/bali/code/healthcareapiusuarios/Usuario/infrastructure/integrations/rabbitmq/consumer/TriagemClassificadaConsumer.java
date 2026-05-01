package br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.consumer;

import br.com.bali.code.healthcareapiusuarios.Usuario.domain.service.NotificationService;
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

    @RabbitListener(queues = "${queue.triagem.classificada}")
    public void consume(TriagemClassificadaPayload payload) {
        log.info("Recebida triagem classificada: {}", payload);
        try {
            notificationService.notifyDoctor(payload);
        } catch (Exception e) {
            log.error("Erro ao processar notificação de triagem: {}", e.getMessage());
        }
    }
}
