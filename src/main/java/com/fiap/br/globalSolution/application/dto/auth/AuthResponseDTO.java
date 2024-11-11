package com.fiap.br.globalSolution.application.dto.auth;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
public class AuthResponseDTO extends RepresentationModel<AuthResponseDTO> {
    private UUID id;
    private String name;
    private String email;
    private String message;
}
