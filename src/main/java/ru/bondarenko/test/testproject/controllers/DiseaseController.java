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
import ru.bondarenko.test.testproject.controllers.view.DiseaseView;
import ru.bondarenko.test.testproject.controllers.view.mappers.DiseaseMapper;
import ru.bondarenko.test.testproject.services.DiseaseService;
import ru.bondarenko.test.testproject.services.exception.MedcardNotCreatedException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Медицинская карта пациента", description = "Позволяет просмотреть, изменить и удалить медицинскую карту пациента")
@RequiredArgsConstructor
public class DiseaseController {
    private final DiseaseService diseaseService;
    private final DiseaseMapper diseaseMapper;

    @Operation(
            summary = "Просмотр всех заболеваний пациента",
            description = "Позволяет найти список заболеваний выбранного пациента по ID пациента"
    )
    @GetMapping("/patient/{patient_id}/diseases")
    public List<DiseaseView> getAllDiseases(@PathVariable("patient_id") int patientId) {
        return diseaseService.getAllDiseases(patientId).stream().map(diseaseMapper::convertToDiseaseView)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Просмотр медицинской карты пациента",
            description = "Позволяет найти медицинскую карту выбранного пациента по ID заболевания"
    )
    @GetMapping("/patient/disease/{disease_id}")
    public DiseaseView getDisease(@PathVariable("disease_id") int diseaseId) {
        return diseaseMapper.convertToDiseaseView(diseaseService.findDisease(diseaseId));
    }

    @Operation(summary = "Создание медицинской карты пациента")
    @PostMapping("/patient/{patient_id}/disease")
    public ResponseEntity<HttpStatus> createDisease(@RequestBody @Valid DiseaseView diseaseView,
                                                    @PathVariable("patient_id") int patientId,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MedcardNotCreatedException(errors.toString());
        }
        diseaseService.newDisease(diseaseMapper.convertToDisease(diseaseView), patientId, diseaseView.getMkb10Id());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Обновление медицинской карты пациента",
            description = "Позволяет обновить данные заболевания по ID пациента и ID заболевания"
    )
    @PutMapping("/patient/{patient_id}/disease/{disease_id}")
    public ResponseEntity<HttpStatus> updateMedcard(@RequestBody @Valid DiseaseView diseaseView,
                                                    @PathVariable("disease_id") int diseaseId,
                                                    @PathVariable("patient_id") int patientId) {
        diseaseService.updateDisease(diseaseMapper.convertToDisease(diseaseView), diseaseId, patientId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление заболевания",
            description = "Позволяет удалить заболевание пациента из медицинской карты"
    )
    @DeleteMapping("/patient/disease/{disease_id}")
    public void deleteDisease(@PathVariable("disease_id") int id) {
        diseaseService.deleteDisease(id);
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handleException(MedcardNotCreatedException e) {
        AppErrorResponse response = new AppErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
