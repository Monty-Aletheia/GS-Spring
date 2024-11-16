package com.fiap.br.globalSolution.application.service;

import com.fiap.br.globalSolution.application.dto.device.DeviceResponseDTO;
import com.fiap.br.globalSolution.application.dto.user.UserRequestDTO;
import com.fiap.br.globalSolution.application.dto.user.UserResponseDTO;
import com.fiap.br.globalSolution.application.dto.user.UserWithDevicesResponseDTO;
import com.fiap.br.globalSolution.application.errors.NotFoundException;
import com.fiap.br.globalSolution.application.service.mapper.DeviceMapper;
import com.fiap.br.globalSolution.application.service.mapper.UserMapper;
import com.fiap.br.globalSolution.domain.model.Device;
import com.fiap.br.globalSolution.domain.model.User;
import com.fiap.br.globalSolution.infra.repository.DeviceRepository;
import com.fiap.br.globalSolution.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final UserMapper userMapper;
    private final DeviceMapper deviceMapper;

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
    public UserWithDevicesResponseDTO addDevicesToUser(UUID userId, List<UUID> deviceIds) {
        User user = findUserById(userId);
        List<Device> devices = findDevicesByIds(deviceIds);
        user.getDevices().addAll(devices);
        userRepository.save(user);
        return userMapper.toUserWithDevicesDto(user);
    }

    @Transactional
    public UserResponseDTO removeDevicesFromUser(UUID userId, List<UUID> deviceIds) {
        User user = findUserById(userId);
        List<Device> devicesToRemove = findDevicesByIds(deviceIds);
        user.getDevices().removeAll(devicesToRemove);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public List<DeviceResponseDTO> getAllUserDevices(UUID userId) {
        User user = findUserById(userId);
        return user.getDevices().stream()
                .map(deviceMapper::toDto)
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
