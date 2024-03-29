package com.ridewise.backend.mapper;

import com.ridewise.backend.dto.UserDto;
import com.ridewise.backend.dto.UserRegisterDto;
import com.ridewise.backend.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverMapper {

    DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);

    Driver registerDtoToEntity(UserRegisterDto registerDto);
    default UserDto mapToDto(Driver driver) {
        return new UserDto(driver.getId(), driver.getFirstName(), driver.getLastName(),
                driver.getEmail(), driver.getRole());
    }
}
