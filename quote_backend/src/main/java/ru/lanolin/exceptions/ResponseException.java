package ru.lanolin.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.lanolin.domain.StatusMessage;

@Getter
public class ResponseException extends RuntimeException {
    private final HttpStatus status;
    private final StatusMessage statusMessage;

    public ResponseException(HttpStatus status, StatusMessage message) {
        this.status = status;
        this.statusMessage = message;
    }

}
