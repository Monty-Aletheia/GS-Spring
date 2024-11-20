package com.fiap.br.globalSolution.application.controller;

import com.fiap.br.globalSolution.application.dto.device.DeviceRequestDTO;
import com.fiap.br.globalSolution.application.dto.device.DeviceResponseDTO;
import com.fiap.br.globalSolution.application.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/devices")
@Tag(name = "Devices", description = "Operações para gerenciamento de dispositivos")
public class DeviceController {

    private final DeviceService deviceService;

    @Operation(summary = "Get all devices", description = "Fetches a paginated list of all devices.")
    @GetMapping
    public ResponseEntity<EntityModel<Page<DeviceResponseDTO>>> getAllDevices(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<DeviceResponseDTO> devicesPage = deviceService.getDevices(pageable);


        return ResponseEntity.ok(createDevicePageEntityModel(devicesPage, page, size));
    }

    @Operation(summary = "Get a device by ID", description = "Fetches a single device by their unique ID.")
    @GetMapping("/{deviceId}")
    public ResponseEntity<EntityModel<DeviceResponseDTO>> getDeviceById(@PathVariable UUID deviceId) {
        DeviceResponseDTO responseDTO = deviceService.getDeviceById(deviceId);

        return ResponseEntity.ok(createDeviceEntityModel(responseDTO, deviceId));
    }

    @Operation(summary = "Get a device by name", description = "Fetches a single device by their unique Name.")
    @GetMapping("/find/{deviceName}")
    public ResponseEntity<EntityModel<DeviceResponseDTO>> getDeviceByName(@PathVariable String deviceName) {
        DeviceResponseDTO responseDTO = deviceService.getDeviceByName(deviceName);

        return ResponseEntity.ok(createDeviceEntityModel(responseDTO, responseDTO.getId()));
    }

    @Operation(summary = "Create new device", description = "Register a new device in database.")
    @PostMapping
    public ResponseEntity<EntityModel<DeviceResponseDTO>> createDevice(@RequestBody @Valid DeviceRequestDTO deviceRequestDTO) {
        DeviceResponseDTO responseDTO = deviceService.createDevice(deviceRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createDeviceEntityModel(responseDTO, responseDTO.getId()));
    }

    @Operation(summary = "Update a device", description = "Updates the details of an existing device.")
    @PutMapping("/{deviceId}")
    public ResponseEntity<EntityModel<DeviceResponseDTO>> updateDevice(@PathVariable UUID deviceId, @RequestBody @Valid DeviceRequestDTO deviceRequestDTO) {
        DeviceResponseDTO responseDTO = deviceService.updateDevice(deviceId, deviceRequestDTO);

        return ResponseEntity.ok(createDeviceEntityModel(responseDTO, deviceId));
    }

    @Operation(summary = "Delete a device by ID", description = "Deletes a device by their unique ID.")
    @DeleteMapping("/{deviceId}")
    public ResponseEntity<?> deleteDevice(@PathVariable UUID deviceId) {
        deviceService.deleteDevice(deviceId);

        return ResponseEntity.noContent().build();
    }

    // ===========================================
    // =             HELPER METHODS              =
    // ===========================================

    private EntityModel<DeviceResponseDTO> createDeviceEntityModel(DeviceResponseDTO device, UUID deviceId) {
        EntityModel<DeviceResponseDTO> deviceResource = EntityModel.of(device);
        Link selfLink = linkTo(DeviceController.class).slash(deviceId).withSelfRel();
        Link allDevicesLink = linkTo(DeviceController.class).withRel("devices");
        deviceResource.add(selfLink);
        deviceResource.add(allDevicesLink);
        return deviceResource;
    }

    private EntityModel<Page<DeviceResponseDTO>> createDevicePageEntityModel(Page<DeviceResponseDTO> devicesPage, int page, int size) {
        return EntityModel.of(devicesPage,
                linkTo(methodOn(DeviceController.class).getAllDevices(page, size)).withSelfRel());
    }
}
