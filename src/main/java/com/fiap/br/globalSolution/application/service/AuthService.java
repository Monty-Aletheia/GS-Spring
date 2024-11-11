package com.fiap.br.globalSolution.application.service;

import com.fiap.br.globalSolution.application.dto.auth.AuthResponseDTO;
import com.fiap.br.globalSolution.application.dto.auth.LoginDTO;
import com.fiap.br.globalSolution.application.dto.auth.RegisterDTO;
import com.fiap.br.globalSolution.application.errors.NotFoundException;
import com.fiap.br.globalSolution.application.errors.UnauthorizedException;
import com.fiap.br.globalSolution.application.service.mapper.UserMapper;
import com.fiap.br.globalSolution.domain.model.User;
import com.fiap.br.globalSolution.infra.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthResponseDTO register(RegisterDTO registerDTO) {
        User user = userMapper.toEntity(registerDTO);
        User savedUser = userRepository.save(user);
        AuthResponseDTO response = userMapper.toDto(savedUser);
        response.setMessage("User registered successfully");
        return response;
    }

    public AuthResponseDTO login(LoginDTO loginDTO) {
        Optional<User> existUser = userRepository.findByEmail(loginDTO.getEmail());

        if (existUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        User user = existUser.get();

        if (Objects.equals(loginDTO.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        AuthResponseDTO response = userMapper.toDto(user);
        response.setMessage("Login successful");

        return response;
    }
}
