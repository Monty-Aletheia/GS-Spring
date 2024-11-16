package com.fiap.br.globalSolution.application.dto.device;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceAssociationDTO extends RepresentationModel<DeviceAssociationDTO> {
    @Schema(description = "list of appliance/device IDs")
    @NotNull
    private List<UserDeviceDTO> userDevices;
}


