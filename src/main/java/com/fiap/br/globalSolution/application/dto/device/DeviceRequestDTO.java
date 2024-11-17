package com.fiap.br.globalSolution.application.dto.device;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeviceRequestDTO {

    @Schema(description = "Name of the device", example = "Smart Thermostat")
    @NotNull
    private String name;

    @Schema(description = "Category of the device", example = "Home Automation")
    @NotNull
    private String category;

    @Schema(description = "Model of the device", example = "Nest Thermostat v3")
    @NotNull
    private String model;

    @Schema(description = "Power rating of the device in watts", example = "150.0")
    @NotNull
    private Double powerRating;

}
