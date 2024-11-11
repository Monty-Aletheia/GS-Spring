package com.fiap.br.globalSolution.application.service.mapper;

import com.fiap.br.globalSolution.application.dto.auth.AuthResponseDTO;
import com.fiap.br.globalSolution.application.dto.auth.RegisterDTO;
import com.fiap.br.globalSolution.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(RegisterDTO dto);

    AuthResponseDTO toDto(User user);
}
