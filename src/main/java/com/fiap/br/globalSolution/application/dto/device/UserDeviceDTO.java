package com.fiap.br.globalSolution.application.dto.device;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDeviceDTO{
    @NotNull
    private UUID deviceId;
    @NotNull
    private Double estimatedUsageHours;
}
