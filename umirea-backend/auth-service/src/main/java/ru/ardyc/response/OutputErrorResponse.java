package ru.ardyc.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OutputErrorResponse extends ErrorResponse {
    private String error;

    public OutputErrorResponse(String error) {
        super();
        this.error = error;
    }
}
