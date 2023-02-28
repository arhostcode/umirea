package ru.ardyc.auth.errors;

import ru.ardyc.response.ErrorResponse;

public class NotSuccessfulVerificationError extends ErrorResponse {
    private String message;

    public NotSuccessfulVerificationError() {
        message = "Not successful verification.";
        code = 4;
    }
}
