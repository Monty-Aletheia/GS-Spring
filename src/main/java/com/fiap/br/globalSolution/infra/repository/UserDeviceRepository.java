package com.fiap.br.globalSolution.infra.repository;

import com.fiap.br.globalSolution.domain.model.UserDevice;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserDeviceRepository extends JpaRepository<UserDevice, UUID> {

    List<UserDevice> findByUserId(UUID userId);
    Page<UserDevice> findByUserId(UUID userId, Pageable pageable);

    boolean existsByIdIn(@NotNull List<UUID> deviceIds);
}
