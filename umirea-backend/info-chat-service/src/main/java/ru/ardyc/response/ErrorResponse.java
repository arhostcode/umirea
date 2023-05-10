package ru.ardyc.response;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ErrorResponse extends Response {
    public ErrorResponse() {
        this.responseType = ResponseType.ERROR;
    }
}
