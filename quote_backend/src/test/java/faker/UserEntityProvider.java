package faker;

import net.datafaker.providers.base.AbstractProvider;
import ru.lanolin.domain.AuthEntity;
import ru.lanolin.domain.UserEntity;
import ru.lanolin.domain.dto.UserEntityDto;

import java.util.UUID;

public class UserEntityProvider extends AbstractProvider<QuoteFaker> {

    protected UserEntityProvider(QuoteFaker faker) {
        super(faker);
    }

    public UserEntity userEntity() {
        AuthEntity auth = faker.authEntity().authEntity();
        return new UserEntity(id(), name(), auth, icon());
    }

    public UserEntityDto userEntityDto() {
        return new UserEntityDto(id(), name(), icon());
    }

    public UUID id() {
        return UUID.randomUUID();
    }

    public String name() {
        return faker.name().username();
    }

    public String icon() {
        return faker.file().fileName();
    }

}
