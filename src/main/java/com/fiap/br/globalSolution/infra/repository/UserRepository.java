package com.fiap.br.globalSolution.infra.repository;

import com.fiap.br.globalSolution.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);
    Optional<User> findByFirebaseId(String firebaseId);
    Optional<User> findByEmail(String email);
}
