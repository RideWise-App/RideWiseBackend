package com.ridewise.backend.mapper;

import com.ridewise.backend.constants.Roles;
import com.ridewise.backend.entity.Client;
import com.ridewise.backend.entity.VerificationToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VerificationTokenMapper {

    VerificationTokenMapper INSTANCE = Mappers.getMapper(VerificationTokenMapper.class);

    default VerificationToken mapToken(String token, Long userId, Roles role) {
        return VerificationToken.builder().user(userId).token(token).role(role).build();
    }
}
