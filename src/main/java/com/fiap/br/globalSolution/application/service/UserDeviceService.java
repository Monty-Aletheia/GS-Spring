package com.fiap.br.globalSolution.application.service;

import com.fiap.br.globalSolution.application.dto.device.DeviceAssociationDTO;
import com.fiap.br.globalSolution.application.dto.userDevice.UserDeviceDTO;
import com.fiap.br.globalSolution.application.dto.userDevice.UserDeviceRemoveDTO;
import com.fiap.br.globalSolution.application.dto.userDevice.UserDeviceResponseDTO;
import com.fiap.br.globalSolution.application.errors.BadRequestException;
import com.fiap.br.globalSolution.application.errors.NotFoundException;
import com.fiap.br.globalSolution.application.service.mapper.UserDeviceMapper;
import com.fiap.br.globalSolution.domain.model.Device;
import com.fiap.br.globalSolution.domain.model.User;
import com.fiap.br.globalSolution.domain.model.UserDevice;
import com.fiap.br.globalSolution.infra.repository.DeviceRepository;
import com.fiap.br.globalSolution.infra.repository.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class UserDeviceService {

    private final DeviceRepository deviceRepository;
    private final UserDeviceRepository userDeviceRepository;

    private final UserDeviceMapper userDeviceMapper;
    private final UserService userService;

    public Page<UserDeviceResponseDTO> getAllUserDevices(UUID userId, Pageable pageable) {
        Page<UserDevice> userDevicesPage = userDeviceRepository.findByUserId(userId, pageable);
        return userDevicesPage.map(userDeviceMapper::toDto);
    }

    public void addDevicesToUser(UUID userId, DeviceAssociationDTO dto) {
        User user = userService.findUserById(userId);

        List<Device> devices = findDevicesByIds(
                dto.getUserDevices().stream()
                        .map(UserDeviceDTO::getDeviceId)
                        .toList()
        );

        List<UserDevice> userDevices = userDeviceMapper.toEntityList(dto.getUserDevices(), user, devices);

        userDeviceRepository.saveAll(userDevices);
    }

    public UserDeviceResponseDTO updateUserDevice(UUID userDeviceId, UUID userId, Double estimatedUsageHours) {
        UserDevice userDevice = userDeviceRepository.findById(userDeviceId)
                .orElseThrow(() -> new NotFoundException("Device not found or not associated with the user"));

        if(!userDevice.getUser().getId().equals(userId)) {
            throw new BadRequestException("Device is not associated with the user");
        }

        userDeviceMapper.updateEntityFromDto(estimatedUsageHours, userDevice);

        userDeviceRepository.saveAndFlush(userDevice);

        return userDeviceMapper.toDto(userDevice);
    }

    public void removeDevicesFromUser(UUID userId, UserDeviceRemoveDTO dto) {
        userService.findUserById(userId);

        if(!userDeviceRepository.existsByIdIn(dto.getDeviceIds())) {
            throw new NotFoundException("Device not found or not associated with the user");
        }

        userDeviceRepository.deleteAllById(dto.getDeviceIds());
    }

    // ============================
    // =          HELPERS         =
    // ============================

    private List<Device> findDevicesByIds(List<UUID> deviceIds) {
        return deviceIds.stream()
                .map(deviceId -> deviceRepository.findById(deviceId)
                        .orElseThrow(() -> new NotFoundException("One or more devices not found")))
                .toList();
    }

}
