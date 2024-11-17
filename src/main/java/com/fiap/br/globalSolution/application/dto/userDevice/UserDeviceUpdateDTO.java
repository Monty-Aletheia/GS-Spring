package com.fiap.br.globalSolution.application.dto.userDevice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDeviceUpdateDTO {
    @Schema(description = "Estimated daily usage hours", example = "8.0")
    @NotNull
    private Double estimatedUsageHours;
}
