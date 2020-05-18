package ru.mkk.lab2.exceptions;

public class IncorrectTypeException extends RuntimeException {
    public IncorrectTypeException() {
        super("Некорректный тип");
    }
}
