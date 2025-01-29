package ru.bondarenko.test.testproject.controllers.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Сущность справочника кодов mkb10")
public class MKB10View {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Schema(description = "Код mkb10", example = "А01.0", accessMode = Schema.AccessMode.READ_ONLY)
    private String code;

    @NotNull
    @Schema(description = "Наименование заболевания", accessMode = Schema.AccessMode.READ_ONLY)
    private String diseaseName;
}
