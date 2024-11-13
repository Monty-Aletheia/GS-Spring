package com.fiap.br.globalSolution.infra.repository;

import com.fiap.br.globalSolution.domain.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
}
