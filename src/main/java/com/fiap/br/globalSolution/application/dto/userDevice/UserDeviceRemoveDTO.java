package com.fiap.br.globalSolution.application.dto.userDevice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserDeviceRemoveDTO {
    @NotNull(message = "Device IDs list cannot be null")
    @NotEmpty(message = "Device IDs list cannot be empty")
    @Valid
    @Size(min = 1, message = "At least one device ID must be provided")
    @Schema(description = "list of appliance/device IDs to remove")
    private List<UUID> deviceIds;
}
