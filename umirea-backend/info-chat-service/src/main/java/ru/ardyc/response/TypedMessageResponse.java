package ru.ardyc.response;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class TypedMessageResponse<T> extends Response {
    private T message;

    public TypedMessageResponse(T message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TypedMessageResponse{" +
                "message=" + message +
                '}';
    }

    public T getMessage() {
        return message;
    }

    public TypedMessageResponse() {
    }
}