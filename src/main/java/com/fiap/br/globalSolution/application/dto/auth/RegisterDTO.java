package com.fiap.br.globalSolution.application.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotEmpty
    private String name;
    @Email
    private String email;
    @NotEmpty
    private String password;
}
