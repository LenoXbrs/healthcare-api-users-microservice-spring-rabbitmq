package br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração RabbitMQ da api-usuarios.
 *
 * Princípios aplicados:
 * - Cada serviço declara as filas que CONSOME (triagem.classificada)
 * - Filas durable=true: sobrevivem a restarts do broker
 * - DLQ configurada: falhas de notificação não perdem mensagens
 * - Exchange declarada aqui pois este serviço precisa garantir sua existência
 */
@Configuration
public class RabbitMQConfig {

    // ── Fila consumida por este serviço ────────────────────────────────────
    public static final String QUEUE_TRIAGEM_CLASSIFICADA     = "triagem.classificada";
    public static final String QUEUE_TRIAGEM_CLASSIFICADA_DLQ = "triagem.classificada.dlq";

    // ── Exchange de entrada (publicada pela api-triagem) ──────────────────
    public static final String EXCHANGE_TRIAGEM               = "triagem.exchange";

    // ── Exchange de DLQ ───────────────────────────────────────────────────
    public static final String EXCHANGE_DLQ                   = "dlq.exchange";

    // ── Routing keys ──────────────────────────────────────────────────────
    public static final String RK_TRIAGEM_CLASSIFICADA        = "triagem.classificada";
    public static final String RK_TRIAGEM_CLASSIFICADA_DLQ    = "triagem.classificada.dlq";

    // ────────────────────────────────────────────────────────────────────────
    // Exchanges
    // ────────────────────────────────────────────────────────────────────────

    @Bean
    public DirectExchange triagemExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_TRIAGEM).durable(true).build();
    }

    @Bean
    public DirectExchange dlqExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_DLQ).durable(true).build();
    }

    // ────────────────────────────────────────────────────────────────────────
    // Dead Letter Queue para notificações falhas
    // ────────────────────────────────────────────────────────────────────────

    @Bean
    public Queue triagemClassificadaDlq() {
        return QueueBuilder.durable(QUEUE_TRIAGEM_CLASSIFICADA_DLQ).build();
    }

    @Bean
    public Binding dlqBinding(Queue triagemClassificadaDlq, DirectExchange dlqExchange) {
        return BindingBuilder.bind(triagemClassificadaDlq)
                .to(dlqExchange)
                .with(RK_TRIAGEM_CLASSIFICADA_DLQ);
    }

    // ────────────────────────────────────────────────────────────────────────
    // Fila principal — aponta para DLQ em caso de falha
    // ────────────────────────────────────────────────────────────────────────

    @Bean
    public Queue triagemClassificadaQueue() {
        return QueueBuilder.durable(QUEUE_TRIAGEM_CLASSIFICADA)
                .withArgument("x-dead-letter-exchange", EXCHANGE_DLQ)
                .withArgument("x-dead-letter-routing-key", RK_TRIAGEM_CLASSIFICADA_DLQ)
                .build();
    }

    @Bean
    public Binding triagemClassificadaBinding(Queue triagemClassificadaQueue,
                                               DirectExchange triagemExchange) {
        return BindingBuilder.bind(triagemClassificadaQueue)
                .to(triagemExchange)
                .with(RK_TRIAGEM_CLASSIFICADA);
    }

    // ────────────────────────────────────────────────────────────────────────
    // Serialização JSON
    // ────────────────────────────────────────────────────────────────────────

    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
