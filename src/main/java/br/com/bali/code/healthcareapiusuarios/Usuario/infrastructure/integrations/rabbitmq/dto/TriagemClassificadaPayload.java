package br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.dto;

public record TriagemClassificadaPayload(
    Long medicoId,
    String pacienteNome,
    String prioridade
) {}
