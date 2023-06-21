package ru.lanolin.domain.dto;


import java.util.UUID;

/**
 * DTO for {@link ru.lanolin.domain.AuthEntity}
 */
public record AuthEntityDto(UUID id, String login, String email, String password) {
}
