package com.fiap.br.globalSolution.application.service;

import com.fiap.br.globalSolution.application.dto.device.DeviceRequestDTO;
import com.fiap.br.globalSolution.application.dto.device.DeviceResponseDTO;
import com.fiap.br.globalSolution.application.errors.BadRequestException;
import com.fiap.br.globalSolution.application.errors.NotFoundException;
import com.fiap.br.globalSolution.application.service.mapper.DeviceMapper;
import com.fiap.br.globalSolution.domain.model.Device;
import com.fiap.br.globalSolution.infra.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public Page<DeviceResponseDTO> getDevices(Pageable pageable) {
        return deviceRepository.findAll(pageable).map(deviceMapper::toDto);
    }

    public DeviceResponseDTO getDeviceByName(String name) {
        Device device = deviceRepository.findDeviceByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("Device with name %s not found", name)));

        return deviceMapper.toDto(device);
    }

    public DeviceResponseDTO getDeviceById(UUID deviceId) {
        Device device = findDeviceById(deviceId);
        return deviceMapper.toDto(device);
    }

    public DeviceResponseDTO createDevice(DeviceRequestDTO deviceRequestDTO) {
        Device device = deviceMapper.toEntity(deviceRequestDTO);

        deviceRepository.findDeviceByName(device.getName())
                .ifPresent(d -> {
                    throw new BadRequestException(String.format("Device with name '%s' already exists", d.getName()));
                });

        device = deviceRepository.save(device);
        return deviceMapper.toDto(device);
    }

    public DeviceResponseDTO updateDevice(UUID deviceId, DeviceRequestDTO deviceRequestDTO) {
        Device device = findDeviceById(deviceId);

        deviceRepository.findDeviceByName(device.getName())
                .ifPresent(d -> {
                    throw new BadRequestException(String.format("Device with name '%s' already exists", d.getName()));
                });

        deviceMapper.updateEntity(device, deviceRequestDTO);

        device = deviceRepository.save(device);

        return deviceMapper.toDto(device);
    }

    public void deleteDevice(UUID deviceId) {
        findDeviceById(deviceId);

        deviceRepository.deleteById(deviceId);
    }

    // ============================
    // =          HELPERS         =
    // ============================

    private Device findDeviceById(UUID id){
        return deviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Device not found"));
    }

}
