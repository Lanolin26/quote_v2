package ut.ru.lanolin.utils.converter;

import faker.QuoteFaker;
import faker.SourceEntityProvider;
import faker.SourceTypeEntityProvider;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.lanolin.domain.SourceEntity;
import ru.lanolin.domain.SourceTypeEntity;
import ru.lanolin.domain.dto.SourceEntityDto;
import ru.lanolin.utils.converter.EntityToDtoConverter;
import ru.lanolin.utils.converter.SourceEntityConverter;
import ru.lanolin.utils.converter.SourceTypeEntityConverter;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Source Entity Converter")
@Log4j2
class SourceEntityConverterTest {

    private static EntityToDtoConverter<SourceEntity, SourceEntityDto> converter;
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

        SourceTypeEntityConverter sourceTypeEntityConverter = new SourceTypeEntityConverter();
        converter = new SourceEntityConverter(sourceTypeEntityConverter);
    }

    static Stream<Arguments> data() {
        Stream.Builder<Arguments> builder = Stream.builder();
        SourceEntityProvider provider = faker.sourceEntity();
        SourceTypeEntityProvider provider1 = faker.sourceTypeEntity();

        for (int i = 0; i < count_test; i++) {
            UUID id = provider.id();
            String name = provider.name();

            SourceTypeEntity typeEntity = provider1.sourceType();

            SourceEntity entity = new SourceEntity(id, typeEntity, name);
            SourceEntityDto dto = new SourceEntityDto(id, typeEntity.getId(), typeEntity.getName(), name);
            builder.accept(arguments(entity, dto));
        }

        return builder.build();
    }

    @ParameterizedTest(name = "{index} = {0}")
    @MethodSource("data")
    void ToEntity(SourceEntity excepted, SourceEntityDto given) {
        // GIVEN
        // WHEN
        SourceEntity entity = converter.toEntity(given);
        // THEN
        assertAll(
                () -> assertEquals(excepted, entity),
                () -> assertEquals(excepted.getId(), entity.getId()),
                () -> assertEquals(excepted.getName(), entity.getName()),
                () -> assertEquals(excepted.getType(), entity.getType()),
                () -> assertEquals(excepted.getType().getId(), entity.getType().getId()),
                () -> assertEquals(excepted.getType().getName(), entity.getType().getName())
        );
    }

    @ParameterizedTest(name = "{index} = {0}")
    @MethodSource("data")
    void ToDTO(SourceEntity given, SourceEntityDto excepted) {
        // GIVEN
        // WHEN
        SourceEntityDto dto = converter.toDTO(given);
        // THEN
        assertAll(
                () -> assertEquals(excepted, dto),
                () -> assertEquals(excepted.id(), dto.id()),
                () -> assertEquals(excepted.name(), dto.name()),
                () -> assertEquals(excepted.typeId(), dto.typeId()),
                () -> assertEquals(excepted.typeName(), dto.typeName())
        );
    }
}