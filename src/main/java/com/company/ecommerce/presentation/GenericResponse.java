package com.company.ecommerce.presentation;

public class GenericResponse<T> {
    private T data;
    private String message;

    public GenericResponse() {
        super();
    }

    public GenericResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public GenericResponse(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
