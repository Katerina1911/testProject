package ru.bondarenko.test.testproject.controllers.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Сущность заболевания")
public class DiseaseView {
    @PastOrPresent(message = "Дата начала болезни не может быть в будущем")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Дата начала болезни", example = "2025-01-01")
    private LocalDate onsetDateOfIllness;

    @PastOrPresent(message = "Дата окончания болезни не может быть в будущем")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Дата окончания болезни", example = "2025-01-01")
    private LocalDate endDateOfIllness;

    @NotBlank(message = "Назначения не должны быть пустыми")
    @Size(min = 1, max = 1024, message = "Назначения должны быть не более 1024 символов")
    @Schema(description = "Назначенное лечение")
    private String descriptionOfTreatment;

    @Schema(description = "Открытие листа нетрудоспособности")
    @NotNull
    private Boolean disabilityCertificate;

    private Integer mkb10Id;
}
