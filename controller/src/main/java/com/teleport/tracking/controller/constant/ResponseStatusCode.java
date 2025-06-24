package com.teleport.tracking.controller.constant;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ResponseStatusCode {
    SUCCESS(200, "Success"),

    BAD_REQUEST(400, "Bad Request"),
    INTERNAL_SERVER_ERROR(500, "Sorry, there must be something wrong with the system");

    private final int httpCode;
    private final String message;

    ResponseStatusCode(int httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    }

    private static final Map<Integer, ResponseStatusCode> codeMapping;

    static {
        codeMapping = Arrays.stream(values()).collect(
                Collectors.toMap(ResponseStatusCode::getHttpCode,
                        Function.identity(), (val1, val2) -> val2));
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getMessage() {
        return message;
    }

    public static ResponseStatusCode getByCodeIfPresent(int code) {
        return Optional.ofNullable(codeMapping.get(code))
                .orElseGet(() -> ResponseStatusCode.INTERNAL_SERVER_ERROR);
    }
}
