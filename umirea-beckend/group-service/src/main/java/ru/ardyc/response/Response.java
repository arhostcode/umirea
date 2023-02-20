package ru.ardyc.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Response {
    protected ResponseType responseType = ResponseType.OK;
    protected int code = 0;
}
