package ru.ardyc.response;

public class MessageResponse extends Response {
    private Object message;

    public MessageResponse(Object message) {
        this.message = message;
    }

    public static MessageResponse create(Object message) {
        return new MessageResponse(message);
    }
}
