package ru.bondarenko.test.testproject.controllers.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Сущность пациента")
public class PatientView {
    @NotBlank(message = "Фамилия не должна быть пустой")
    @Pattern(regexp = "[-а-яА-ЯёЁ\\s]+$")
    @Size(min = 2, max = 100, message = "Фамилия должна быть от 2 до 100 символов длиной")
    @Schema(description = "Фамилия пациента", example = "Петров")
    private String secondName;

    @NotBlank(message = "Имя не должно быть пустым")
    @Schema(description = "Имя пациента", example = "Пётр")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Pattern(regexp = "[-а-яА-ЯёЁ\\s]+$")
    private String firstName;

    @Schema(description = "Отчество пациента (при наличии)", example = "Петрович")
    @Size(min = 2, max = 100, message = "Отчество должно быть от 2 до 100 символов длиной")
    @Pattern(regexp = "[-а-яА-ЯёЁ\\s]+$")
    private String patronymic;

    @NotNull
    @Schema(description = "Пол пациента", allowableValues = {"М", "Ж"})
    @Min(1)
    @Max(2)
    private int sex;

    @Past(message = "Дата рождения не может быть в будущем")
    @Schema(description = "Дата рождения пациента", example = "1970-01-01")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    @Positive
    @Schema(description = "Номер медицинского полиса пациента", example = "2368546289745136")
    @NotNull
    @Digits(integer = 16, fraction = 0, message = "Номер полиса должен состоять из 16 цифр")
    private long policeNumber;
}
