package ru.lanolin.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusMessage {

    private Status status;
    private String error;
    private String message;

    public enum Status {
        OK, ERROR
    }

}
