package faker;

import net.datafaker.providers.base.AbstractProvider;
import ru.lanolin.domain.QuoteEntity;
import ru.lanolin.domain.SourceEntity;
import ru.lanolin.domain.SourceTypeEntity;
import ru.lanolin.domain.UserEntity;
import ru.lanolin.domain.dto.QuoteEntityDto;

import java.util.UUID;

public class QuoteEntityProvider extends AbstractProvider<QuoteFaker> {

    protected QuoteEntityProvider(QuoteFaker faker) {
        super(faker);
    }

    public QuoteEntity quoteEntity() {
        UserEntity user = faker.userEntity().userEntity();
        SourceEntity source = faker.sourceEntity().sourceEntity();
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setId(id());
        quoteEntity.setSource(source);
        quoteEntity.setAuthor(user);
        quoteEntity.setText(text());
        return quoteEntity;
    }

    public QuoteEntityDto quoteEntityDto() {
        UserEntity user = faker.userEntity().userEntity();
        SourceEntity source = faker.sourceEntity().sourceEntity();
        SourceTypeEntity type = source.getType();
        return new QuoteEntityDto(
                id(), source.getId(), type.getId(), user.getId(),
                type.getName(), source.getName(), user.getName(), user.getIcon(),
                text()
        );
    }

    public UUID id() {
        return UUID.randomUUID();
    }

    public String text() {
        return faker.lorem().characters(10, 512);
    }

}
