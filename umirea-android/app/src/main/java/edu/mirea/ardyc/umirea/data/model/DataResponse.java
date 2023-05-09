package edu.mirea.ardyc.umirea.data.model;

public class DataResponse<T> {
    private final T data;
    private final String message;

    public DataResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public DataResponse(T data) {
        this.data = data;
        this.message = null;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static <T> DataResponse<T> error(String message) {
        return new DataResponse<>(null, message);
    }

    public boolean isError() {
        return data == null;
    }
}
