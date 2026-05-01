package br.com.bali.code.healthcareapiusuarios.Usuario.api.resource;

import br.com.bali.code.healthcareapiusuarios.Usuario.api.model.request.RegisterUserRequest;
import br.com.bali.code.healthcareapiusuarios.Usuario.api.model.response.RegisterUserResponse;
import br.com.bali.code.healthcareapiusuarios.Usuario.domain.service.UserServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController()
@RequestMapping("/user")
public class UserController {

    private final UserServiceImp userService;

    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @Operation(
            summary = "create a user",
            description = "user of the system"
    )
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody @Valid RegisterUserRequest payload ){

        return ResponseEntity.ok(payload.);
    }


}
