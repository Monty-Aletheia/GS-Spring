package com.fiap.br.globalSolution.application.service.mapper;

import com.fiap.br.globalSolution.application.dto.device.UserDeviceDTO;
import com.fiap.br.globalSolution.domain.model.Device;
import com.fiap.br.globalSolution.domain.model.User;
import com.fiap.br.globalSolution.domain.model.UserDevice;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserDeviceMapper {

    public UserDevice toEntity(UserDeviceDTO userDeviceDTO, User user, Device device) {
        if (userDeviceDTO == null || device == null) {
            return null;
        }

        // Mapeamento manual do DTO para a entidade
        UserDevice userDevice = new UserDevice();

        // Definir o consumo com base na fórmula de potência e uso estimado
        userDevice.setEstimatedUsageHours(userDeviceDTO.getEstimatedUsageHours());

        userDevice.setConsumption(calculateConsumption(device.getPowerRating(), userDeviceDTO.getEstimatedUsageHours()));

        // Definir o dispositivo no UserDevice, baseado no dispositivo atual
        userDevice.setDevice(device);

        // Defina o usuário no UserDevice
        userDevice.setUser(user);

        // Adicionar qualquer outra propriedade do DTO para a entidade, se necessário
        // userDevice.setOtherProperty(userDeviceDTO.getOtherProperty());

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
                .orElse(null); // Retorna null se não encontrar o dispositivo
    }
}
