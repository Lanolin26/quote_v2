package ut.ru.lanolin.utils.converter;

import faker.QuoteFaker;
import faker.UserEntityProvider;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.lanolin.domain.UserEntity;
import ru.lanolin.domain.dto.UserEntityDto;
import ru.lanolin.utils.converter.EntityToDtoConverter;
import ru.lanolin.utils.converter.UserEntityConverter;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("User Entity Converter")
@Log4j2
class UserEntityConverterTest {

    private static EntityToDtoConverter<UserEntity, UserEntityDto> converter;
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

        converter = new UserEntityConverter();
    }


    static Stream<Arguments> data() {
        Stream.Builder<Arguments> builder = Stream.builder();
        UserEntityProvider userEntityProvider = faker.userEntity();

        for (int i = 0; i < count_test; i++) {
            UUID id = userEntityProvider.id();
            String name = userEntityProvider.name();
            boolean icon = userEntityProvider.icon();

            UserEntity entity = new UserEntity(id, name, null, icon);
            UserEntityDto dto = new UserEntityDto(id, name, icon);
            builder.accept(arguments(entity, dto));
        }

        return builder.build();
    }

    @ParameterizedTest(name = "{index} = {0}")
    @MethodSource("data")
    void ToEntity(UserEntity excepted, UserEntityDto given) {
        // GIVEN
        // WHEN
        UserEntity entity = converter.toEntity(given);
        // THEN
        assertAll(
                () -> assertEquals(excepted, entity),
                () -> assertEquals(excepted.getId(), entity.getId()),
                () -> assertEquals(excepted.isIcon(), entity.isIcon()),
                () -> assertEquals(excepted.getName(), entity.getName())
        );
    }

    @ParameterizedTest(name = "{index} = {0}")
    @MethodSource("data")
    void ToDTO(UserEntity given, UserEntityDto excepted) {
        // GIVEN
        // WHEN
        UserEntityDto entity = converter.toDTO(given);
        // THEN
        assertAll(
                () -> assertEquals(excepted, entity),
                () -> assertEquals(excepted.id(), entity.id()),
                () -> assertEquals(excepted.icon(), entity.icon()),
                () -> assertEquals(excepted.name(), entity.name())
        );
    }
}