package com.fiap.br.globalSolution.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "model")
    private String model;

    @Column(name = "power_rating")
    private Double powerRating;  // Potência média em watts

    @Column(name = "estimated_usage_hours")
    private Double estimatedUsageHours;  // Horas estimadas de uso diário

    @Column(name = "consumption")
    private Double consumption;  // Consumo estimado em kWh

    @ManyToMany(mappedBy = "devices")
    private List<User> users;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
