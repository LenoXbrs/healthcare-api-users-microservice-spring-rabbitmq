package br.com.bali.code.healthcareapiusuarios.Usuario.domain.service;

import br.com.bali.code.healthcareapiusuarios.Usuario.api.model.response.NotificacaoResponse;
import br.com.bali.code.healthcareapiusuarios.Usuario.domain.model.Notificacao;
import br.com.bali.code.healthcareapiusuarios.Usuario.domain.repository.NotificacaoRepository;
import br.com.bali.code.healthcareapiusuarios.Usuario.infrastructure.exception.NotificacaoNaoEncontradaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class NotificacaoServiceImp implements NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoServiceImp(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    @Override
    public List<NotificacaoResponse> listarPorUsuario(Long usuarioId, Boolean lida) {
        List<Notificacao> notificacoes = lida == null
                ? notificacaoRepository.findByUsuarioIdOrderByCriadoEmDesc(usuarioId)
                : notificacaoRepository.findByUsuarioIdAndLidaOrderByCriadoEmDesc(usuarioId, lida);

        return notificacoes.stream().map(this::toResponse).toList();
    }

    @Override
    public long contarNaoLidas(Long usuarioId) {
        return notificacaoRepository.countByUsuarioIdAndLidaFalse(usuarioId);
    }

    @Override
    @Transactional
    public NotificacaoResponse marcarComoLida(Long id, Long usuarioId) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new NotificacaoNaoEncontradaException(id));

        if (!notificacao.getUsuarioId().equals(usuarioId)) {
            throw new NotificacaoNaoEncontradaException(id);
        }

        notificacao.setLida(true);
        return toResponse(notificacaoRepository.save(notificacao));
    }

    private NotificacaoResponse toResponse(Notificacao n) {
        return new NotificacaoResponse(
                n.getId(),
                n.getUsuarioId(),
                n.getTipo(),
                n.getTitulo(),
                n.getMensagem(),
                n.getTriagemId(),
                n.getLida(),
                n.getCriadoEm()
        );
    }
}
