package com.ridewise.backend.mapper;

import com.ridewise.backend.dto.ClientRegisterDto;
import com.ridewise.backend.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client registerDtoToEntity(ClientRegisterDto registerDto);
}
