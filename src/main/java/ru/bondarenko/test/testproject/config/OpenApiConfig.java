package ru.bondarenko.test.testproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(
        title = "Medcard API",
        description = "Медицинская карта пациента",
        version = "1.0.0"
    )
)

public class OpenApiConfig {
}
