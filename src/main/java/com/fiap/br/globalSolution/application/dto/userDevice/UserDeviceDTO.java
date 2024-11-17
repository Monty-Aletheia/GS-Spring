package com.fiap.br.globalSolution.application.dto.userDevice;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDeviceDTO{
    @NotNull
    @Schema(description = "Unique identifier of the device", example = "783AB34F-18F1-4C56-946D-28FB336AA939")
    private UUID deviceId;
    @NotNull
    @Schema(description = "Estimated daily usage hours", example = "8.0")
    private Double estimatedUsageHours;
}
