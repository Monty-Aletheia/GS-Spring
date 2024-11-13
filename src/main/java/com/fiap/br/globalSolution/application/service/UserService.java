package com.fiap.br.globalSolution.application.service;

import com.fiap.br.globalSolution.application.dto.user.UserRequestDTO;
import com.fiap.br.globalSolution.application.dto.user.UserResponseDTO;
import com.fiap.br.globalSolution.application.errors.NotFoundException;
import com.fiap.br.globalSolution.application.service.mapper.UserMapper;
import com.fiap.br.globalSolution.domain.model.User;
import com.fiap.br.globalSolution.infra.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }


    public UserResponseDTO getUserById(UUID id) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isPresent()) {
            return userMapper.toDto(optUser.get());
        }

        throw new NotFoundException("User not found");
    }

    public UserResponseDTO updateUser(UUID id, UserRequestDTO dto) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        User user = optUser.get();

        userMapper.updateEntityFromDto(dto, user);

        return userMapper.toDto(user);
    }

    public void deleteUser(UUID id) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        userRepository.deleteById(id);
    }
}
