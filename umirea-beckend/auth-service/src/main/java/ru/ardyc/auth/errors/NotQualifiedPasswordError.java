package ru.ardyc.auth.errors;

import ru.ardyc.response.ErrorResponse;

public class NotQualifiedPasswordError extends ErrorResponse {
    private String message;

    public NotQualifiedPasswordError() {
        message = "Not qualified password.";
        code = 3;
    }
}
