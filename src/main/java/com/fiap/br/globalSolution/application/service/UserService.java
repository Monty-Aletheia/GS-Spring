package com.fiap.br.globalSolution.application.service;

import com.fiap.br.globalSolution.application.dto.device.DeviceAssociationDTO;
import com.fiap.br.globalSolution.application.dto.device.DeviceResponseDTO;
import com.fiap.br.globalSolution.application.dto.device.UserDeviceDTO;
import com.fiap.br.globalSolution.application.dto.user.UserRequestDTO;
import com.fiap.br.globalSolution.application.dto.user.UserResponseDTO;
import com.fiap.br.globalSolution.application.errors.NotFoundException;
import com.fiap.br.globalSolution.application.service.mapper.DeviceMapper;
import com.fiap.br.globalSolution.application.service.mapper.UserDeviceMapper;
import com.fiap.br.globalSolution.application.service.mapper.UserMapper;
import com.fiap.br.globalSolution.domain.model.Device;
import com.fiap.br.globalSolution.domain.model.User;
import com.fiap.br.globalSolution.domain.model.UserDevice;
import com.fiap.br.globalSolution.infra.repository.DeviceRepository;
import com.fiap.br.globalSolution.infra.repository.UserDeviceRepository;
import com.fiap.br.globalSolution.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final UserDeviceRepository userDeviceRepository;

    private final UserMapper userMapper;
    private final DeviceMapper deviceMapper;
    private final UserDeviceMapper userDeviceMapper = new UserDeviceMapper();

    // ==========================================
    // =                USERS                   =
    // ==========================================

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserResponseDTO getUserById(UUID id) {
        User user = findUserById(id);
        return userMapper.toDto(user);
    }

    @Transactional
    public UserResponseDTO updateUser(UUID id, UserRequestDTO dto) {
        User user = findUserById(id);
        userMapper.updateEntityFromDto(dto, user);
        return userMapper.toDto(user);
    }

    @Transactional
    public void deleteUser(UUID id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    // =======================================
    // =              DEVICES                =
    // =======================================

    @Transactional
    public void addDevicesToUser(UUID userId, DeviceAssociationDTO dto) {
        User user = findUserById(userId);

        List<Device> devices = findDevicesByIds(
                dto.getUserDevices().stream()
                        .map(UserDeviceDTO::getDeviceId)
                        .toList()
        );

        List<UserDevice> userDevices = userDeviceMapper.toEntityList(dto.getUserDevices(), user, devices);

        userDeviceRepository.saveAll(userDevices);
    }


    @Transactional
    public void removeDevicesFromUser(UUID userId, DeviceAssociationDTO dto) {
        findUserById(userId);

        List<UUID> deviceIds = dto.getUserDevices().stream()
                .map(UserDeviceDTO::getDeviceId)
                .toList();

        userDeviceRepository.deleteByUserIdAndDeviceIdIn(userId, deviceIds);
    }

    @Transactional
    public List<DeviceResponseDTO> getAllUserDevices(UUID userId) {
        List<UserDevice> userDevices = userDeviceRepository.findByUserId(userId);

        return userDevices.stream()
                .map(userDevice -> {
                    DeviceResponseDTO deviceResponseDTO = deviceMapper.toDto(userDevice.getDevice());
                    deviceResponseDTO.setEstimatedUsageHours(userDevice.getEstimatedUsageHours());
                    deviceResponseDTO.setConsumption(userDevice.getConsumption());
                    return deviceResponseDTO;
                })
                .toList();
    }


    // =======================================
    // =              HELPERS                =
    // =======================================

    private User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private List<Device> findDevicesByIds(List<UUID> deviceIds) {
        List<Device> devices = deviceRepository.findAllById(deviceIds);
        if (devices.size() != deviceIds.size()) {
            throw new NotFoundException("One or more devices not found");
        }
        return devices;
    }
}
