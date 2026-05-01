package br.com.bali.code.healthcareapiusuarios.Usuario.api.model.request;

import br.com.bali.code.healthcareapiusuarios.Usuario.application.Role;

public record RegisterUserRequest(String name, String email, String password, Role role) {
}
