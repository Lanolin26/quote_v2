package ru.lanolin.utils.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lanolin.domain.QuoteEntity;
import ru.lanolin.domain.SourceEntity;
import ru.lanolin.domain.SourceTypeEntity;
import ru.lanolin.domain.UserEntity;
import ru.lanolin.domain.dto.QuoteEntityDto;
import ru.lanolin.domain.dto.SourceEntityDto;
import ru.lanolin.domain.dto.UserEntityDto;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class QuoteEntityConverter implements EntityToDtoConverter<QuoteEntity, QuoteEntityDto> {

    private final EntityToDtoConverter<UserEntity, UserEntityDto> userEntityConverter;
    private final EntityToDtoConverter<SourceEntity, SourceEntityDto> sourceEntityConverter;

    @Override
    public QuoteEntity toEntity(QuoteEntityDto quoteEntityDto) {
        QuoteEntity entity = new QuoteEntity();

        entity.setId(quoteEntityDto.id());
        entity.setText(quoteEntityDto.text());

        UserEntityDto userEntityDto = new UserEntityDto(quoteEntityDto.authorId(), quoteEntityDto.authorName(), quoteEntityDto.authorIcon());
        entity.setAuthor(userEntityConverter.toEntity(userEntityDto));

        SourceEntityDto sourceEntityDto = new SourceEntityDto(quoteEntityDto.sourceId(), quoteEntityDto.sourceTypeId(), quoteEntityDto.sourceTypeName(), quoteEntityDto.sourceName());
        entity.setSource(sourceEntityConverter.toEntity(sourceEntityDto));

        return entity;
    }

    @Override
    public QuoteEntityDto toDTO(QuoteEntity quoteEntity) {
        UserEntity author = quoteEntity.getAuthor();
        SourceEntity source = quoteEntity.getSource();
        SourceTypeEntity sourceTypeEntity = getIfNonNull(source, SourceEntity::getType);

        return new QuoteEntityDto(
                quoteEntity.getId(),
                getIfNonNull(source, SourceEntity::getId),
                getIfNonNull(sourceTypeEntity, SourceTypeEntity::getId),
                getIfNonNull(author, UserEntity::getId),
                getIfNonNull(sourceTypeEntity, SourceTypeEntity::getName),
                getIfNonNull(source, SourceEntity::getName),
                getIfNonNull(author, UserEntity::getName),
                getIfNonNull(author, UserEntity::isIcon),
                quoteEntity.getText()
        );
    }

}
