package com.fiap.br.globalSolution.application.service.mapper;

import com.fiap.br.globalSolution.application.dto.userDevice.UserDeviceDTO;
import com.fiap.br.globalSolution.application.dto.userDevice.UserDeviceResponseDTO;
import com.fiap.br.globalSolution.domain.model.Device;
import com.fiap.br.globalSolution.domain.model.User;
import com.fiap.br.globalSolution.domain.model.UserDevice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserDeviceMapper {

    public UserDevice toEntity(UserDeviceDTO userDeviceDTO, User user, Device device) {
        if (userDeviceDTO == null || device == null) {
            return null;
        }

        UserDevice userDevice = new UserDevice();

        userDevice.setEstimatedUsageHours(userDeviceDTO.getEstimatedUsageHours());

        userDevice.setConsumption(calculateConsumption(device.getPowerRating(), userDeviceDTO.getEstimatedUsageHours()));

        userDevice.setDevice(device);

        userDevice.setUser(user);

        return userDevice;
    }

    public List<UserDevice> toEntityList(List<UserDeviceDTO> userDeviceDTOs, User user, List<Device> devices) {
        if (userDeviceDTOs == null || devices == null) {
            return null;
        }

        return userDeviceDTOs.stream()
                .map(userDeviceDTO -> toEntity(userDeviceDTO, user, findDeviceById(devices, userDeviceDTO.getDeviceId())))
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(double estimatedUsageHours, UserDevice entity) {
        entity.setEstimatedUsageHours(estimatedUsageHours);
        double consumption = calculateConsumption(entity.getDevice().getPowerRating(), estimatedUsageHours);
        entity.setConsumption(consumption);
    }

    public UserDeviceResponseDTO toDto(UserDevice entity) {
        UserDeviceResponseDTO dto = new UserDeviceResponseDTO();
        Device device = entity.getDevice();

        dto.setId(entity.getId());
        dto.setEstimatedUsageHours(entity.getEstimatedUsageHours());
        dto.setConsumption(entity.getConsumption());
        dto.setModel(device.getModel());
        dto.setPowerRating(device.getPowerRating());
        dto.setCategory(device.getCategory());
        dto.setName(device.getName());

        return dto;
    }


    private Double calculateConsumption(Double powerRating, Double estimatedUsageHours) {
        if (powerRating == null || estimatedUsageHours == null) {
            return 0.0;
        }
        return (powerRating / 1000) * estimatedUsageHours; // kWh
    }

    private Device findDeviceById(List<Device> devices, UUID deviceId) {
        if (devices == null || deviceId == null) {
            return null;
        }

        return devices.stream()
                .filter(device -> device.getId().equals(deviceId))
                .findFirst()
                .orElse(null);
    }
}
