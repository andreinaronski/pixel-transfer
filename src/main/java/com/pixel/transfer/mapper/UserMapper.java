package com.pixel.transfer.mapper;

import com.pixel.transfer.dto.UserDto;
import com.pixel.transfer.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "emails", source = "emails")
    @Mapping(target = "phones", source = "phones")
    @Mapping(target = "balance", source = "account.balance")
    UserDto toDto(User user);
}
