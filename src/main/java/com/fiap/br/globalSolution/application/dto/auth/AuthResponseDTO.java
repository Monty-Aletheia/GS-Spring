package com.fiap.br.globalSolution.application.dto.auth;

import com.fiap.br.globalSolution.application.dto.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class AuthResponseDTO extends RepresentationModel<AuthResponseDTO> {
    private String message;
    private UserResponseDTO user;
}
