package ru.lanolin.utils.converter;

import org.springframework.stereotype.Component;
import ru.lanolin.domain.AuthEntity;
import ru.lanolin.domain.dto.AuthEntityDto;

@Component
public class AuthEntityConverter implements EntityToDtoConverter<AuthEntity, AuthEntityDto> {
    @Override
    public AuthEntity toEntity(AuthEntityDto o) {
        AuthEntity authEntity = new AuthEntity();
        authEntity.setId(o.id());
        authEntity.setLogin(o.login());
        authEntity.setEmail(o.email());
        authEntity.setPassword(o.password());
        return authEntity;
    }

    @Override
    public AuthEntityDto toDTO(AuthEntity authEntity) {
        return new AuthEntityDto(
                authEntity.getId(),
                authEntity.getLogin(),
                authEntity.getEmail(),
                null);
    }
}
