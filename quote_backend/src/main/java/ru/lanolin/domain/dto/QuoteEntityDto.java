package ru.lanolin.domain.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ru.lanolin.domain.QuoteEntity}
 */
public record QuoteEntityDto(
        UUID id, UUID sourceId, UUID sourceTypeId,  UUID authorId,
        String sourceTypeName, String sourceName, String authorName, String authorIcon,
        String text
) implements Serializable {
}