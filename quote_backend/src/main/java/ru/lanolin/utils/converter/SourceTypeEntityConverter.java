package ru.lanolin.utils.converter;

import org.springframework.stereotype.Component;
import ru.lanolin.domain.SourceTypeEntity;
import ru.lanolin.domain.dto.SourceTypeEntityDto;

@Component
public class SourceTypeEntityConverter implements EntityToDtoConverter<SourceTypeEntity, SourceTypeEntityDto> {
    @Override
    public SourceTypeEntity toEntity(SourceTypeEntityDto dto) {
        SourceTypeEntity sourceTypeEntity = new SourceTypeEntity();
        sourceTypeEntity.setId(dto.id());
        sourceTypeEntity.setName(dto.name());
        return sourceTypeEntity;
    }

    @Override
    public SourceTypeEntityDto toDTO(SourceTypeEntity entity) {
        return new SourceTypeEntityDto(entity.getId(), entity.getName());
    }
}
