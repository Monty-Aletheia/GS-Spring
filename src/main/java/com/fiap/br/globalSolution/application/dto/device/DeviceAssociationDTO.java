package com.fiap.br.globalSolution.application.dto.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceAssociationDTO extends RepresentationModel<DeviceAssociationDTO> {
    @Schema(description = "list of appliance/device IDs")
    private List<UUID> deviceIds;
}
