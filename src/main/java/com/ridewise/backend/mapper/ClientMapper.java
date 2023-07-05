package com.ridewise.backend.mapper;

import com.ridewise.backend.dto.UserDto;
import com.ridewise.backend.dto.UserRegisterDto;
import com.ridewise.backend.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client registerDtoToEntity(UserRegisterDto registerDto);

    default UserDto mapToDto(Client client) {
        return new UserDto(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getRole()
        );
    }
}
