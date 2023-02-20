package ru.ardyc.auth.errors;

import ru.ardyc.response.ErrorResponse;

public class UserNotFoundError extends ErrorResponse {

    private String message;

    public UserNotFoundError() {
        message = "This user not found";
        code = 1;
    }

}
