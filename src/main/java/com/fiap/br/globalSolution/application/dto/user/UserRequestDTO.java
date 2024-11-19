package com.fiap.br.globalSolution.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotEmpty
    @Schema(description = "Name of the user", example = "John Doe")
    private String name;

    @Email
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @NotEmpty
    @Schema(description = "Password for the user account", example = "securePassword123")
    private String password;

    @Schema(description = "Id of firebase", example = "faf83085-2259-409e-a5dd-0a5348872532")
    private String firebaseId;
}
