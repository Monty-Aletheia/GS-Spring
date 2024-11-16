package com.fiap.br.globalSolution.application.dto.user;

import com.fiap.br.globalSolution.application.dto.device.DeviceResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserWithDevicesResponseDTO extends UserResponseDTO{

    @Schema(description = "User device list")
    private List<DeviceResponseDTO> devices;
}
