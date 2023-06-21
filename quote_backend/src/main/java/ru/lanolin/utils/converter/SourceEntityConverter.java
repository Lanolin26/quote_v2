package ru.lanolin.utils.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lanolin.domain.SourceEntity;
import ru.lanolin.domain.SourceTypeEntity;
import ru.lanolin.domain.dto.SourceEntityDto;
import ru.lanolin.domain.dto.SourceTypeEntityDto;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SourceEntityConverter implements EntityToDtoConverter<SourceEntity, SourceEntityDto>{

    private final EntityToDtoConverter<SourceTypeEntity, SourceTypeEntityDto> sourceTypeEntityConverter;

    @Override
    public SourceEntity toEntity(SourceEntityDto sourceEntityDto) {
        SourceEntity entity = new SourceEntity();
        entity.setId(sourceEntityDto.id());
        entity.setName(sourceEntityDto.name());
        SourceTypeEntityDto dto = new SourceTypeEntityDto(sourceEntityDto.typeId(), sourceEntityDto.typeName());
        entity.setType(sourceTypeEntityConverter.toEntity(dto));
        return entity;
    }

    @Override
    public SourceEntityDto toDTO(SourceEntity sourceEntity) {
        return new SourceEntityDto(
                sourceEntity.getId(),
                getIfNonNull(sourceEntity.getType(), SourceTypeEntity::getId),
                getIfNonNull(sourceEntity.getType(), SourceTypeEntity::getName),
                sourceEntity.getName()
        );
    }
}
