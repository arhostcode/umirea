package ru.ardyc.response;

public class ErrorResponse extends Response {
    public ErrorResponse() {
        this.responseType = ResponseType.ERROR;
    }
}
