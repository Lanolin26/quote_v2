package ru.lanolin.utils.converter;

import org.springframework.stereotype.Component;
import ru.lanolin.domain.UserEntity;
import ru.lanolin.domain.dto.UserEntityDto;

@Component
public class UserEntityConverter implements EntityToDtoConverter<UserEntity, UserEntityDto> {

    @Override
    public UserEntity toEntity(UserEntityDto userEntityDto) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userEntityDto.id());
        userEntity.setName(userEntityDto.name());
        userEntity.setIcon(userEntityDto.icon());
        userEntity.setAuth(null); //TODO: copy ?

        return userEntity;
    }

    @Override
    public UserEntityDto toDTO(UserEntity userEntity) {
        return new UserEntityDto(userEntity.getId(), userEntity.getName(), userEntity.getIcon());
    }
}
