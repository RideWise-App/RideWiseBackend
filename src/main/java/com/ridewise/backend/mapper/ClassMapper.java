package com.ridewise.backend.mapper;

import com.ridewise.backend.dto.ClassDto;
import com.ridewise.backend.entity.Class;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClassMapper {

    ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

    default ClassDto mapDto (Class classToMap) {
        return new ClassDto(classToMap.getId(), classToMap.getClassType(), classToMap.getRate());
    }
}
