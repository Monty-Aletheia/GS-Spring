package com.fiap.br.globalSolution.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotEmpty
    @Schema(description = "Name of the user", example = "John Doe", required = true)
    private String name;

    @Email
    @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
    private String email;

    @NotEmpty
    @Schema(description = "Password for the user account", example = "securePassword123", required = true)
    private String password;
}
