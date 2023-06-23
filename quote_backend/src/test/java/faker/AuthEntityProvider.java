package faker;

import net.datafaker.providers.base.AbstractProvider;
import ru.lanolin.domain.AuthEntity;
import ru.lanolin.domain.dto.AuthEntityDto;

import java.util.UUID;

public class AuthEntityProvider extends AbstractProvider<QuoteFaker> {

    protected AuthEntityProvider(QuoteFaker faker) {
        super(faker);
    }

    public AuthEntity authEntity() {
        AuthEntity authEntity = new AuthEntity();
        authEntity.setId(id());
        authEntity.setEmail(email());
        authEntity.setPassword(password());
        authEntity.setLogin(login());
        return authEntity;
    }

    public AuthEntityDto authEntityDto() {
        return new AuthEntityDto(id(), login(), email(), password());
    }

    public UUID id() {
        return UUID.randomUUID();
    }

    public String login() {
        return faker.name().username();
    }

    public String password() {
        return faker.internet().password(8, 24);
    }

    public String email() {
        return faker.internet().emailAddress();
    }

}
