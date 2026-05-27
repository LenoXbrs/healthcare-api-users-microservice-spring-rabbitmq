package br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.consumer;

import br.com.bali.code.healthcareapiusuarios.Usuario.domain.service.NotificationService;
import br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.config.RabbitMQConfig;
import br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.dto.TriagemCriadaPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TriagemCriadaConsumer {

    private final NotificationService notificationService;

    public TriagemCriadaConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TRIAGEM_CRIADA)
    public void consume(TriagemCriadaPayload payload) {
        log.info("[triagem.criada] Recebida: triagemId={} paciente={}",
                payload.triagemId(), payload.pacienteNome());
        notificationService.notificarEnfermeirosTriagemCriada(payload);
    }
}
