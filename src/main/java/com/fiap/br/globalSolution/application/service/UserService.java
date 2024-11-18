package com.fiap.br.globalSolution.application.service;

import com.fiap.br.globalSolution.application.dto.user.UserRequestDTO;
import com.fiap.br.globalSolution.application.dto.user.UserResponseDTO;
import com.fiap.br.globalSolution.application.errors.NotFoundException;
import com.fiap.br.globalSolution.application.service.mapper.UserMapper;
import com.fiap.br.globalSolution.domain.model.User;
import com.fiap.br.globalSolution.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    public UserResponseDTO getUserById(UUID id) {
        User user = findUserById(id);
        return userMapper.toDto(user);
    }

    public UserResponseDTO getUserByFirebaseId(UUID firebaseId) {
        User user = userRepository.findByFirebaseId(firebaseId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    public UserResponseDTO updateUser(UUID id, UserRequestDTO dto) {
        User user = findUserById(id);
        userMapper.updateEntity(dto, user);
        return userMapper.toDto(user);
    }

    public void deleteUser(UUID id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    // ============================
    // =          HELPERS         =
    // ============================

    protected User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
