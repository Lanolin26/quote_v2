package ru.lanolin.exceptions;


import lombok.Getter;

import java.io.IOException;

@Getter
public class FileOperationException extends IOException {


    private final FileOperation fileOperation;

    public FileOperationException(FileOperation fileOperation) {
        this.fileOperation = fileOperation;
    }

    public FileOperationException(FileOperation fileOperation, String message) {
        super(message);
        this.fileOperation = fileOperation;
    }

    public FileOperationException(FileOperation fileOperation, String message, Throwable cause) {
        super(message, cause);
        this.fileOperation = fileOperation;
    }

    public FileOperationException(FileOperation fileOperation, Throwable cause) {
        super(cause);
        this.fileOperation = fileOperation;
    }

    public enum FileOperation {
        READ, WRITE, DELETE
    }
}
