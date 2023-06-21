package ru.lanolin.domain.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ru.lanolin.domain.SourceTypeEntity}
 */
public record SourceTypeEntityDto(UUID id, String name) implements Serializable {

}