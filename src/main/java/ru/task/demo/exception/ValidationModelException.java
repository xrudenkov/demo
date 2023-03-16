package ru.task.demo.exception;

public class ValidationModelException extends RuntimeException {
    public ValidationModelException(String message) {
        super(message);
    }
}
