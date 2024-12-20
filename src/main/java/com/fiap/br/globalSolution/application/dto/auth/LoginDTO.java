package com.fiap.br.globalSolution.application.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDTO {
    @Email
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @NotEmpty
    @Schema(description = "Password for the user account", example = "securePassword123")
    private String password;
}
