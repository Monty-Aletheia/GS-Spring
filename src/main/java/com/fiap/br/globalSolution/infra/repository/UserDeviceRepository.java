package com.fiap.br.globalSolution.infra.repository;

import com.fiap.br.globalSolution.domain.model.UserDevice;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;
import java.util.UUID;

public interface UserDeviceRepository extends JpaRepository<UserDevice, UUID> {

    Page<UserDevice> findByUserId(UUID userId, Pageable pageable);

    boolean existsByIdIn(@NotNull List<UUID> deviceIds);

    @Procedure(procedureName = "insert_user_devices")
    UUID insertUserDevices(UUID p_device_id, UUID p_user_id, Double p_power_rating, Double p_estimated_usage_hours);

    default void insertUserDevices(UserDevice p_user_device) {
        UUID id = insertUserDevices(
                p_user_device.getDevice().getId(),
                p_user_device.getUser().getId(),
                p_user_device.getDevice().getPowerRating(),
                p_user_device.getEstimatedUsageHours()
        );
    }
}
