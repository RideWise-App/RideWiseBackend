package com.ridewise.backend.mapper;

import com.ridewise.backend.dto.UserRegisterDto;
import com.ridewise.backend.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverMapper {

    DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);

    Driver registerDtoToEntity(UserRegisterDto registerDto);
}
