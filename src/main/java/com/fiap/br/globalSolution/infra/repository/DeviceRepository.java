package com.fiap.br.globalSolution.infra.repository;

import com.fiap.br.globalSolution.domain.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    @Query("SELECT d FROM Device d WHERE LOWER(d.name) = LOWER(:name)")
    Optional<Device> findDeviceByName(final String name);
}
