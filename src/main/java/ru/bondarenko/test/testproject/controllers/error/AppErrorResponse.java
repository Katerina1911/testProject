package ru.bondarenko.test.testproject.controllers.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AppErrorResponse {
    private final String message;
    private final long timestamp;
}
