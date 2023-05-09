package ru.ardyc.response;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MessageResponse extends Response {
    private Object message;

    public MessageResponse(Object message) {
        this.message = message;
    }

    public static MessageResponse create(Object message) {
        return new MessageResponse(message);
    }
}
