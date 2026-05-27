package br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.consumer;

import br.com.bali.code.healthcareapiusuarios.Usuario.domain.service.NotificationService;
import br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.config.RabbitMQConfig;
import br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.dto.TriagemFinalizadaPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TriagemFinalizadaConsumer {

    private final NotificationService notificationService;

    public TriagemFinalizadaConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TRIAGEM_FINALIZADA)
    public void consume(TriagemFinalizadaPayload payload) {
        log.info("[triagem.finalizada] Recebida: triagemId={} paciente={}",
                payload.triagemId(), payload.pacienteNome());
        notificationService.notificarEquipeTriagemFinalizada(payload);
    }
}
