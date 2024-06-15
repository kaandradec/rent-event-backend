package com.rentevent.exception;

import java.io.Serial;

public class FuncErrorException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5003320033602480096L;

    public FuncErrorException(final String message) {
        super(message);
    }
}
