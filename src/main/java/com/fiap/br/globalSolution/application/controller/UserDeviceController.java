package com.fiap.br.globalSolution.application.controller;

import com.fiap.br.globalSolution.application.dto.device.DeviceAssociationDTO;
import com.fiap.br.globalSolution.application.dto.userDevice.UserDeviceRemoveDTO;
import com.fiap.br.globalSolution.application.dto.userDevice.UserDeviceResponseDTO;
import com.fiap.br.globalSolution.application.service.UserDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/devices")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "UserDevices", description = "User Devices management API")
public class UserDeviceController {

    private final UserDeviceService userDeviceService;

    // ===========================================
    // =             USER DEVICES                =
    // ===========================================

    @Operation(summary = "Get devices associated with a user", description = "Fetches a list of devices associated with the user.")
    @GetMapping
    public ResponseEntity<?> getDevicesFromUser(@PathVariable UUID userId) {
        List<UserDeviceResponseDTO> devices = userDeviceService.getAllUserDevices(userId);

        List<EntityModel<UserDeviceResponseDTO>> deviceModels = devices.stream()
                .map(device -> EntityModel.of(device,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserDeviceController.class)
                                .getDevicesFromUser(userId)).withSelfRel()))
                .toList();

        return ResponseEntity.ok(deviceModels);
    }



    @Operation(summary = "Add devices to user", description = "Associates devices with a user.")
    @PostMapping
    public ResponseEntity<?> addDevicesToUser(
            @PathVariable UUID userId, @Valid @RequestBody DeviceAssociationDTO deviceAssociationDTO) {

        userDeviceService.addDevicesToUser(userId, deviceAssociationDTO);

        return ResponseEntity.ok("Devices added to user");
    }

    @Operation(summary = "update device from user", description = "Updated device from a user")
    @PutMapping("{userDeviceId}")
    public ResponseEntity<?> updateDeviceFromUser(
            @PathVariable UUID userDeviceId, @RequestBody double estimatedUsageHours, @PathVariable UUID userId) {

        UserDeviceResponseDTO responseDTO = userDeviceService.updateUserDevice(userDeviceId, userId, estimatedUsageHours);

        EntityModel<UserDeviceResponseDTO> deviceModel = EntityModel.of(responseDTO,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserDeviceController.class)
                        .updateDeviceFromUser(userDeviceId, estimatedUsageHours, userId)).withSelfRel());

        return ResponseEntity.ok(deviceModel);
    }

    @Operation(summary = "Remove devices from user", description = "Removes devices from a user.")
    @DeleteMapping
    public ResponseEntity<?> removeDevicesFromUser(
            @PathVariable UUID userId, @RequestBody @Valid UserDeviceRemoveDTO dto) {

        userDeviceService.removeDevicesFromUser(userId, dto);

        return new ResponseEntity<>("Devices removed from user", HttpStatus.NO_CONTENT);
    }
}

