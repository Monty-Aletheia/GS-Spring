package com.fiap.br.globalSolution.application.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
