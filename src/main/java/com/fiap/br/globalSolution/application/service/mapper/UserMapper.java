package com.fiap.br.globalSolution.application.service.mapper;

import com.fiap.br.globalSolution.application.dto.auth.AuthResponseDTO;
import com.fiap.br.globalSolution.application.dto.auth.RegisterDTO;
import com.fiap.br.globalSolution.application.dto.user.UserRequestDTO;
import com.fiap.br.globalSolution.application.dto.user.UserResponseDTO;
import com.fiap.br.globalSolution.application.dto.user.UserWithDevicesResponseDTO;
import com.fiap.br.globalSolution.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(RegisterDTO dto);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toDto(User user);

    @Mapping(target = "devices", source = "devices")
    UserWithDevicesResponseDTO toUserWithDevicesDto(User user);

    default void updateEntityFromDto(UserRequestDTO dto, User user) {
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
    }
}
