package ru.ardyc.auth.errors;

import ru.ardyc.response.ErrorResponse;

public class AuthError extends ErrorResponse {
    private String message;

    public AuthError(String message) {
        this.message = message;
        code = 2;
    }
}
