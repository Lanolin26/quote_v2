package ut.ru.lanolin.utils.converter;

import faker.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.lanolin.domain.QuoteEntity;
import ru.lanolin.domain.SourceEntity;
import ru.lanolin.domain.SourceTypeEntity;
import ru.lanolin.domain.UserEntity;
import ru.lanolin.domain.dto.QuoteEntityDto;
import ru.lanolin.utils.converter.*;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Quote Entity Converter")
@Log4j2
class QuoteEntityConverterTest {

    private static EntityToDtoConverter<QuoteEntity, QuoteEntityDto> converter;
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
        SourceEntityConverter sourceEntityConverter = new SourceEntityConverter(sourceTypeEntityConverter);
        UserEntityConverter userEntityConverter = new UserEntityConverter();
        converter = new QuoteEntityConverter(userEntityConverter, sourceEntityConverter);
    }

    static Stream<Arguments> data() {
        Stream.Builder<Arguments> builder = Stream.builder();
        QuoteEntityProvider quoteEntityProvider = faker.quoteEntity();
        SourceEntityProvider sourceEntityProvider = faker.sourceEntity();
        SourceTypeEntityProvider sourceTypeEntityProvider = faker.sourceTypeEntity();
        UserEntityProvider userEntityProvider = faker.userEntity();

        for (int i = 0; i < count_test; i++) {

            UUID quoteId = quoteEntityProvider.id();
            String text = quoteEntityProvider.text();

            UUID sourceId = sourceEntityProvider.id();
            String sourceName = sourceEntityProvider.name();

            UUID sourceTypeId = sourceTypeEntityProvider.id();
            String sourceTypeName = sourceTypeEntityProvider.name();

            UUID authorId = userEntityProvider.id();
            String authorName = userEntityProvider.name();
            boolean authorIcon = userEntityProvider.icon();

            QuoteEntity entity = new QuoteEntity(
                    quoteId,
                    new SourceEntity(sourceId,
                            new SourceTypeEntity(sourceTypeId, sourceTypeName),
                            sourceName),
                    new UserEntity(authorId, authorName, null, authorIcon),
                    text
            );

            QuoteEntityDto dto = new QuoteEntityDto(
                    quoteId, sourceId, sourceTypeId, authorId,
                    sourceTypeName, sourceName, authorName, authorIcon,
                    text
            );
            builder.accept(arguments(entity, dto));
        }

        return builder.build();
    }

    @ParameterizedTest(name = "{index} = {0}")
    @MethodSource("data")
    void ToEntity(QuoteEntity excepted, QuoteEntityDto given) {
        // GIVEN
        // WHEN
        QuoteEntity entity = converter.toEntity(given);
        // THEN
        assertAll(
                () -> assertEquals(excepted, entity),

                () -> assertEquals(excepted.getId(), entity.getId()),
                () -> assertEquals(excepted.getText(), entity.getText()),

                () -> assertEquals(excepted.getAuthor(), entity.getAuthor()),
                () -> assertEquals(excepted.getAuthor().getId(), entity.getAuthor().getId()),
                () -> assertEquals(excepted.getAuthor().isIcon(), entity.getAuthor().isIcon()),
                () -> assertEquals(excepted.getAuthor().getName(), entity.getAuthor().getName()),

                () -> assertEquals(excepted.getSource(), entity.getSource()),
                () -> assertEquals(excepted.getSource().getId(), entity.getSource().getId()),
                () -> assertEquals(excepted.getSource().getName(), entity.getSource().getName()),

                () -> assertEquals(excepted.getSource().getType(), entity.getSource().getType()),
                () -> assertEquals(excepted.getSource().getType().getId(), entity.getSource().getType().getId()),
                () -> assertEquals(excepted.getSource().getType().getName(), entity.getSource().getType().getName())
        );
    }

    @ParameterizedTest(name = "{index} = {0}")
    @MethodSource("data")
    void ToDTO(QuoteEntity given, QuoteEntityDto excepted) {
        // GIVEN
        // WHEN
        QuoteEntityDto dto = converter.toDTO(given);
        // THEN
        assertAll(
                () -> assertEquals(excepted, dto),
                () -> assertEquals(excepted.id(), dto.id()),
                () -> assertEquals(excepted.authorIcon(), dto.authorIcon()),
                () -> assertEquals(excepted.authorId(), dto.authorId()),
                () -> assertEquals(excepted.authorName(), dto.authorName()),
                () -> assertEquals(excepted.sourceId(), dto.sourceId()),
                () -> assertEquals(excepted.sourceName(), dto.sourceName()),
                () -> assertEquals(excepted.sourceTypeId(), dto.sourceTypeId()),
                () -> assertEquals(excepted.sourceTypeName(), dto.sourceTypeName()),
                () -> assertEquals(excepted.text(), dto.text())
        );
    }
}