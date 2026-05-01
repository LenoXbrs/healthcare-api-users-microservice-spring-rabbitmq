package br.com.bali.code.healthcareapiusuarios.Usuario.domain.service;

import br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.dto.TriagemClassificadaPayload;

public interface NotificationService {
    void notifyDoctor(TriagemClassificadaPayload payload);
}
