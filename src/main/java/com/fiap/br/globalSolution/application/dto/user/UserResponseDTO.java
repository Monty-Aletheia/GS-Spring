package com.fiap.br.globalSolution.application.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserResponseDTO extends RepresentationModel<UserResponseDTO> {

    @Schema(description = "Unique identifier of the user", example = "d6c77c56-bc7a-4b75-9a5f-b926c3bdf1f8")
    private UUID id;

    @Schema(description = "Name of the user", example = "John Doe")
    private String name;

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;
}
