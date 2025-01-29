package ru.bondarenko.test.testproject.services.exception;

public class MedcardNotCreatedException extends RuntimeException {
    public MedcardNotCreatedException(String msg) {
        super(msg);
    }
}
