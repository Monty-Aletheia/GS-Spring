package com.fiap.br.globalSolution.application.service.mapper;


import com.fiap.br.globalSolution.application.dto.device.DeviceRequestDTO;
import com.fiap.br.globalSolution.application.dto.device.DeviceResponseDTO;
import com.fiap.br.globalSolution.domain.model.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    @Mapping(target = "id", ignore = true)
    Device toEntity(DeviceRequestDTO dto);

    DeviceResponseDTO toDto(Device device);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Device device, DeviceRequestDTO deviceRequestDTO);
}

