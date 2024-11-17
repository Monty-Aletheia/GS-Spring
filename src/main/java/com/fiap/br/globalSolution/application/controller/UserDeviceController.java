package com.fiap.br.globalSolution.application.controller;

import com.fiap.br.globalSolution.application.dto.device.DeviceAssociationDTO;
import com.fiap.br.globalSolution.application.dto.userDevice.UserDeviceRemoveDTO;
import com.fiap.br.globalSolution.application.dto.userDevice.UserDeviceResponseDTO;
import com.fiap.br.globalSolution.application.dto.userDevice.UserDeviceUpdateDTO;
import com.fiap.br.globalSolution.application.service.UserDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<?> getDevicesFromUser(
            @PathVariable UUID userId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<UserDeviceResponseDTO> devices = userDeviceService.getAllUserDevices(userId, pageable);

        var deviceModels = EntityModel.of(devices,
                linkTo(methodOn(UserDeviceController.class).getDevicesFromUser(userId, page, size)).withSelfRel());

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
            @PathVariable UUID userDeviceId, @Valid @RequestBody UserDeviceUpdateDTO dto, @PathVariable UUID userId) {

        UserDeviceResponseDTO responseDTO = userDeviceService.updateUserDevice(userDeviceId, userId, dto.getEstimatedUsageHours());

        EntityModel<UserDeviceResponseDTO> deviceModel = EntityModel.of(responseDTO,
               linkTo(methodOn(UserDeviceController.class).updateDeviceFromUser(userDeviceId, dto, userId)).withSelfRel(),
                linkTo(methodOn(UserDeviceController.class).getDevicesFromUser(userId, 1, 10)).withRel("AllUserDevices"));

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

