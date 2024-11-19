package com.fiap.br.globalSolution.infra.repository;

import com.fiap.br.globalSolution.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);
    Optional<User> findByFirebaseId(String firebaseId);
    Optional<User> findByEmail(String email);

    @Procedure(procedureName = "insert_tb_users")
    UUID insertUser(String p_name, String p_password, String p_email, String p_firebase_id);

    default User insertUser(User user) {
        UUID id = insertUser(user.getFirebaseId(), user.getPassword(), user.getEmail(), user.getFirebaseId());

        return findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
