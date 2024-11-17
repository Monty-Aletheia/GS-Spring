package com.fiap.br.globalSolution.application.service;

import com.fiap.br.globalSolution.application.dto.auth.AuthResponseDTO;
import com.fiap.br.globalSolution.application.dto.auth.LoginDTO;
import com.fiap.br.globalSolution.application.dto.user.UserRequestDTO;
import com.fiap.br.globalSolution.application.dto.user.UserResponseDTO;
import com.fiap.br.globalSolution.application.errors.BadRequestException;
import com.fiap.br.globalSolution.application.errors.NotFoundException;
import com.fiap.br.globalSolution.application.errors.UnauthorizedException;
import com.fiap.br.globalSolution.application.service.mapper.UserMapper;
import com.fiap.br.globalSolution.domain.model.User;
import com.fiap.br.globalSolution.infra.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponseDTO register(UserRequestDTO registerDTO) {
        User user = userMapper.toEntity(registerDTO);

        Optional<User> existUser = userRepository.findByEmail(user.getEmail());

        if(existUser.isPresent()) {
            throw new BadRequestException("This email already in use");
        }

        User savedUser = userRepository.save(user);
        UserResponseDTO userResponse = userMapper.toDto(savedUser);
        String message = "User registered successfully";

        return new AuthResponseDTO(message,userResponse);
    }


    public AuthResponseDTO login(LoginDTO loginDTO) {
        Optional<User> existUser = userRepository.findByEmail(loginDTO.getEmail());

        if (existUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        User user = existUser.get();

        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String message = "Login successful";
        UserResponseDTO userResponse = userMapper.toDto(user);

        return new AuthResponseDTO(message,userResponse);
    }
}
