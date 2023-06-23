package ut.ru.lanolin.utils.converter;

import faker.AuthEntityProvider;
import faker.QuoteFaker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.lanolin.domain.AuthEntity;
import ru.lanolin.domain.dto.AuthEntityDto;
import ru.lanolin.utils.converter.AuthEntityConverter;
import ru.lanolin.utils.converter.EntityToDtoConverter;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Auth Entity Converter")
@Log4j2
class AuthEntityConverterTest {

    private static EntityToDtoConverter<AuthEntity, AuthEntityDto> converter;
    private static QuoteFaker faker;
    private static long count_test;

    @BeforeAll
    static void beforeAll() {
        count_test = 100;
        log.info("Test count = {}", count_test);

        Locale locale = new Locale("ru-RU");
        long seed = (long) (Math.random() * 100000 + 954);
        log.info("Seed = {}", seed);
        Random random = new Random(seed);
        faker = new QuoteFaker(locale, random);

        converter = new AuthEntityConverter();
    }

    static Stream<Arguments> data() {
        Stream.Builder<Arguments> builder = Stream.builder();
        AuthEntityProvider provider = faker.authEntity();

        for (int i = 0; i < count_test; i++) {
            UUID id = provider.id();
            String email = provider.email();
            String login = provider.login();
            String password = provider.password();

            AuthEntity entity = new AuthEntity(id, email, login, password);
            AuthEntityDto dto = new AuthEntityDto(id, email, login, password);
            builder.accept(arguments(entity, dto));
        }

        return builder.build();
    }

    @ParameterizedTest(name = "{index} = {0}")
    @MethodSource("data")
    void ToEntity(AuthEntity excepted, AuthEntityDto given) {
        // GIVEN
        // WHEN
        AuthEntity entity = converter.toEntity(given);
        // THEN
        assertAll(
                () -> assertEquals(excepted, entity),
                () -> assertEquals(excepted.getId(), entity.getId()),
                () -> assertEquals(excepted.getEmail(), entity.getEmail()),
                () -> assertEquals(excepted.getLogin(), entity.getLogin()),
                () -> assertEquals(excepted.getPassword(), entity.getPassword())
        );
    }

    @ParameterizedTest(name = "{index} = {0}")
    @MethodSource("data")
    void ToDTO(AuthEntity given, AuthEntityDto excepted) {
        // GIVEN
        // WHEN
        AuthEntityDto dto = converter.toDTO(given);
        // THEN
        assertAll(
                () -> assertEquals(excepted.id(), dto.id()),
                () -> assertEquals(excepted.email(), dto.email()),
                () -> assertEquals(excepted.login(), dto.login()),
                () -> assertNull(dto.password())
        );
    }
}