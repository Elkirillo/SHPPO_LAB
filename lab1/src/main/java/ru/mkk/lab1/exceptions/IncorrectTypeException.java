package ru.mkk.lab1.exceptions;

public class IncorrectTypeException extends RuntimeException {
    public IncorrectTypeException() {
        super("Несовместимые компоненты");
    }
}
