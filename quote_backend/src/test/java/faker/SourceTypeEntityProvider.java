package faker;

import net.datafaker.providers.base.AbstractProvider;
import ru.lanolin.domain.SourceTypeEntity;
import ru.lanolin.domain.dto.SourceTypeEntityDto;

import java.util.UUID;

public class SourceTypeEntityProvider extends AbstractProvider<QuoteFaker> {

    protected SourceTypeEntityProvider(QuoteFaker faker) {
        super(faker);
    }

    public SourceTypeEntity sourceType() {
        SourceTypeEntity sourceTypeEntity = new SourceTypeEntity();
        sourceTypeEntity.setId(id());
        sourceTypeEntity.setName(name());
        return sourceTypeEntity;
    }

    public SourceTypeEntityDto sourceTypeDto() {
        return new SourceTypeEntityDto(id(), name());
    }

    public UUID id(){
        return UUID.randomUUID();
    }

    public String name() {
        return faker.text().text(3, 25);
    }

}
