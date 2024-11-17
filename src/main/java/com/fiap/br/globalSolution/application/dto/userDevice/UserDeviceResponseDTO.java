package com.fiap.br.globalSolution.application.dto.userDevice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDeviceResponseDTO extends RepresentationModel<UserDeviceResponseDTO> {
    @Schema(description = "Unique identifier of the UserDevice", example = "783AB34F-18F1-4C56-946D-28FB336AA939")
    private UUID id;

    @Schema(description = "Name of the device", example = "Smart Thermostat")
    private String name;

    @Schema(description = "Category of the device", example = "Home Automation")
    private String category;

    @Schema(description = "Model of the device", example = "Nest Thermostat v3")
    private String model;

    @Schema(description = "Power rating of the device in watts", example = "150.0")
    private Double powerRating;

    @Schema(description = "Estimated daily usage hours", example = "8.0")
    private Double estimatedUsageHours;

    @Schema(description = "Estimated consumption of the device in kWh", example = "1.2")
    private Double consumption;
}
