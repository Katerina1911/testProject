package ru.bondarenko.test.testproject.services.exception;

public class PatientNotCreatedException extends RuntimeException {
    public PatientNotCreatedException(String msg) {
        super(msg);
    }
}
