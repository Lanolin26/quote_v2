package ru.lanolin.utils.converter;

import java.util.Objects;
import java.util.function.Function;

public interface EntityToDtoConverter<Entity, DTO> {

    default <T, R> R getIfNonNull(T toCheck, Function<T, R> ifNonNull) {
        return Objects.nonNull(toCheck) ? ifNonNull.apply(toCheck) : null;
    }

    Entity toEntity(DTO dto);

    DTO toDTO(Entity entity);

}
