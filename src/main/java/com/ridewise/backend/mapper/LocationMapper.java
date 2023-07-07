package com.ridewise.backend.mapper;

import com.ridewise.backend.dto.LocationDto;
import com.ridewise.backend.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    default Map<String, Location> mapToEntity(Map<String, LocationDto> dto) {
        Map<String, Location> map = new HashMap<>();
        map.put("start", mapLocation(dto.get("start")));
        map.put("finish", mapLocation(dto.get("finish")));
        return map;
    }

    default Location mapLocation(LocationDto dto) {
        return Location.builder().latitude(dto.latitude()).longitude(dto.longitude()).build();
    }
}
