package faker;

import net.datafaker.providers.base.AbstractProvider;
import ru.lanolin.domain.SourceEntity;
import ru.lanolin.domain.SourceTypeEntity;
import ru.lanolin.domain.dto.SourceEntityDto;
import ru.lanolin.domain.dto.SourceTypeEntityDto;

import java.util.UUID;

public class SourceEntityProvider extends AbstractProvider<QuoteFaker> {

    protected SourceEntityProvider(QuoteFaker faker) {
        super(faker);
    }

    public SourceEntity sourceEntity() {
        SourceEntity sourceEntity = new SourceEntity();
        SourceTypeEntity type = faker.sourceTypeEntity().sourceType();
        sourceEntity.setId(id());
        sourceEntity.setName(name());
        sourceEntity.setType(type);
        return sourceEntity;
    }

    public SourceEntityDto sourceEntityDto() {
        SourceTypeEntityDto type = faker.sourceTypeEntity().sourceTypeDto();
        return new SourceEntityDto(id(), type.id(), type.name(), name());
    }

    public UUID id() {
        return UUID.randomUUID();
    }

    public String name() {
        return faker.text().text(3, 25);
    }

}
