package ru.lanolin.domain.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ru.lanolin.domain.UserEntity}
 */
public record UserEntityDto(UUID id, String name, boolean icon) implements Serializable {
}