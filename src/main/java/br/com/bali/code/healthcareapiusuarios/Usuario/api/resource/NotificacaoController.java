package br.com.bali.code.healthcareapiusuarios.Usuario.api.resource;

import br.com.bali.code.healthcareapiusuarios.Usuario.api.model.response.NotificacaoResponse;
import br.com.bali.code.healthcareapiusuarios.Usuario.domain.service.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notificacoes")
@Tag(name = "Notificações", description = "Notificações assíncronas geradas por eventos de triagem")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @GetMapping
    @Operation(summary = "Listar notificações do usuário")
    public ResponseEntity<List<NotificacaoResponse>> listar(
            @RequestParam Long usuarioId,
            @RequestParam(required = false) Boolean lida) {
        return ResponseEntity.ok(notificacaoService.listarPorUsuario(usuarioId, lida));
    }

    @GetMapping("/contagem")
    @Operation(summary = "Contar notificações não lidas")
    public ResponseEntity<Map<String, Long>> contarNaoLidas(@RequestParam Long usuarioId) {
        long total = notificacaoService.contarNaoLidas(usuarioId);
        return ResponseEntity.ok(Map.of("naoLidas", total));
    }

    @PatchMapping("/{id}/lida")
    @Operation(summary = "Marcar notificação como lida")
    public ResponseEntity<NotificacaoResponse> marcarComoLida(
            @PathVariable Long id,
            @RequestParam Long usuarioId) {
        return ResponseEntity.ok(notificacaoService.marcarComoLida(id, usuarioId));
    }
}
