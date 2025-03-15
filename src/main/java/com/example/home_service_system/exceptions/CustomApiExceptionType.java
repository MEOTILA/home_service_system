package com.example.home_service_system.exceptions;

public enum CustomApiExceptionType {
    BAD_REQUEST(400, "Bad Request"),
    UNPROCESSIABLE_ENTITY(422, "Unprocessiable entity"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String message;

    CustomApiExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
