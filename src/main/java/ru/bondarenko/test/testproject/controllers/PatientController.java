package ru.bondarenko.test.testproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.bondarenko.test.testproject.controllers.error.AppErrorResponse;
import ru.bondarenko.test.testproject.controllers.view.PatientView;
import ru.bondarenko.test.testproject.controllers.view.mappers.PatientMapper;
import ru.bondarenko.test.testproject.services.PatientService;
import ru.bondarenko.test.testproject.services.exception.PatientNotCreatedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Информация о пациенте", description = "Позволяет просмотреть, изменить и удалить пациента из базы данных")
public class PatientController {
    private final PatientMapper patientMapper;
    private final PatientService patientService;

    @Operation(summary = "Поиск пациента по ID")
    @GetMapping("/patient/{id}")
    public PatientView getPatient(@PathVariable("id") int id) {
        return patientMapper.convertToPatientView(patientService.findPatient(id));
    }

    @Operation(
            summary = "Регистрация пациента",
            description = "Позволяет создать запись о новом пациенте"
    )
    @PostMapping("/patient")
    public ResponseEntity<HttpStatus> createPatient(@RequestBody @Valid PatientView patientView,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PatientNotCreatedException(errors.toString());
        }
        patientService.newPatient(patientMapper.convertToPatient(patientView));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Обновление данных пациента",
            description = "Позволяет обновить запись о пациенте"
    )
    @PutMapping("/patient/{id}")
    public ResponseEntity<HttpStatus> updatePatient(@RequestBody @Valid PatientView patientView,
                                                    @PathVariable("id") int id) {
        patientService.updatePatient(patientMapper.convertToPatient(patientView), id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление пациента",
            description = "Позволяет удалить запись о пациенте вместе с его медкартой"
    )
    @DeleteMapping("/patient/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        patientService.deletePatient(id);
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handleException(PatientNotCreatedException e) {
        AppErrorResponse response = new AppErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
