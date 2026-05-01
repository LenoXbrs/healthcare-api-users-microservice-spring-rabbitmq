package br.com.bali.code.healthcareapiusuarios.Usuario.domain.service;

import br.com.bali.code.healthcareapiusuarios.Usuario.domain.model.User;
import br.com.bali.code.healthcareapiusuarios.Usuario.domain.repository.UserRepository;
import br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.integrations.rabbitmq.dto.TriagemClassificadaPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImp implements NotificationService {

    private final UserRepository userRepository;

    public NotificationServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void notifyDoctor(TriagemClassificadaPayload payload) {
        User doctor = userRepository.findById(payload.medicoId())
                .orElse(null);

        String doctorName = (doctor != null) ? doctor.getName() : "ID " + payload.medicoId();

        log.info("### NOTIFICAÇÃO PARA O MÉDICO ###");
        log.info("Médico: {}", doctorName);
        log.info("Paciente: {}", payload.pacienteNome());
        log.info("Prioridade: {}", payload.prioridade());
        log.info("Ação: Por favor, dirija-se à sala de triagem.");
        log.info("#################################");
        
        // Simulação de envio de e-mail/push
        simulateEmailSending(doctor, payload);
    }

    private void simulateEmailSending(User doctor, TriagemClassificadaPayload payload) {
        if (doctor != null && doctor.getEmail() != null) {
            log.info("Simulando envio de e-mail para: {}", doctor.getEmail());
        } else {
            log.warn("Não foi possível enviar e-mail: Médico não encontrado ou sem e-mail cadastrado.");
        }
    }
}
