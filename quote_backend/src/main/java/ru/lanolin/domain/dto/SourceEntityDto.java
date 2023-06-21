package ru.lanolin.domain.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ru.lanolin.domain.SourceEntity}
 */
public record SourceEntityDto(
        UUID id, UUID typeId,
        String typeName, String name
) implements Serializable {
}