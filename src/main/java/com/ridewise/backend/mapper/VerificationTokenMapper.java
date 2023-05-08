package com.ridewise.backend.mapper;

import com.ridewise.backend.entity.Client;
import com.ridewise.backend.entity.VerificationToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VerificationTokenMapper {

    VerificationTokenMapper INSTANCE = Mappers.getMapper(VerificationTokenMapper.class);

    @Mapping(target = "token", source = "token")
    @Mapping(target = "client", source = "client")
    VerificationToken mapToken(String token, Client client);
}
