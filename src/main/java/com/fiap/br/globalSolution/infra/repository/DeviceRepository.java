package com.fiap.br.globalSolution.infra.repository;

import com.fiap.br.globalSolution.domain.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

        Optional<Device> findById(UUID id);

        @Procedure(procedureName = "insert_tb_devices")
        UUID insertDevice(String p_name, String p_category, String p_model, double p_power_rating);

        default Device insertDevice(Device p_device) {
            UUID id = insertDevice(
                    p_device.getName(),
                    p_device.getCategory(),
                    p_device.getModel(),
                    p_device.getPowerRating()
            );

            return findById(id)
                    .orElseThrow(() -> new RuntimeException("Device not found after insertion"));
        }
}
