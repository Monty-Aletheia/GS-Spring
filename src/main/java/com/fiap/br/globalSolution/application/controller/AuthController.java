package com.fiap.br.globalSolution.application.controller;

import com.fiap.br.globalSolution.application.dto.auth.AuthResponseDTO;
import com.fiap.br.globalSolution.application.dto.auth.LoginDTO;
import com.fiap.br.globalSolution.application.dto.auth.RegisterDTO;
import com.fiap.br.globalSolution.application.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "User Login", description = "Allows a registered user to log in by providing their credentials.")
    @PostMapping("/login")
    public ResponseEntity<EntityModel<AuthResponseDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO response = authService.login(loginDTO);

        EntityModel<AuthResponseDTO> resource = EntityModel.of(response);
        Link linkToRegister = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthController.class).register(null)).withRel("register");
        resource.add(linkToRegister);

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "User Registration", description = "Allows a new user to register by providing their details.")
    @PostMapping("/register")
    public ResponseEntity<EntityModel<AuthResponseDTO>> register(@Valid @RequestBody RegisterDTO registerDTO) {
        AuthResponseDTO response = authService.register(registerDTO);

        EntityModel<AuthResponseDTO> resource = EntityModel.of(response);
        Link linkToLogin = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthController.class).login(null)).withRel("login");
        resource.add(linkToLogin);

        return ResponseEntity.ok(resource);
    }
}
