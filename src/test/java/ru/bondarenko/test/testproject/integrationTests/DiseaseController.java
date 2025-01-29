package ru.bondarenko.test.testproject.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bondarenko.test.testproject.controllers.view.DiseaseView;
import ru.bondarenko.test.testproject.controllers.view.mappers.DiseaseMapper;
import ru.bondarenko.test.testproject.models.Disease;
import ru.bondarenko.test.testproject.models.MKB10;
import ru.bondarenko.test.testproject.models.Patient;
import ru.bondarenko.test.testproject.repositories.MKB10Repository;
import ru.bondarenko.test.testproject.services.DiseaseService;
import ru.bondarenko.test.testproject.services.PatientService;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DiseaseController {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DiseaseMapper diseaseMapper;
    @Autowired
    DiseaseService diseaseService;
    @Autowired
    PatientService patientService;
    @Autowired
    MKB10Repository mkb10Repository;

    java.util.Date date = new java.util.Date(1970 - 01 - 01);
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    java.util.Date date1 = new java.util.Date(2024 - 12 - 01);
    LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    java.util.Date date2 = new java.util.Date(2024 - 12 - 11);
    LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    @Test
    void givenDiseaseWhenAddThenStatus201() throws Exception {
        Disease disease = createTestDisease();
        DiseaseView diseaseView = diseaseMapper.convertToDiseaseView(disease);
        int patientId = disease.getPatient().getId();

        mockMvc.perform(post("/patient/{patient_id}/disease", patientId)
                        .content(objectMapper.writeValueAsString(diseaseView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void givenIdWhenGetExistingDiseaseThenStatus200() throws Exception {
        Disease disease = createTestDisease();
        int diseaseId = disease.getId();

        mockMvc.perform(get("/patient/disease/{disease_id}", diseaseId))
                .andExpect(status().isOk());
    }

    @Test
    void giveDiseaseWhenUpdateThenStatus200() throws Exception {
        Disease disease = createTestDisease();
        int diseaseId = disease.getId();
        int patientId = disease.getPatient().getId();
        int mkb10Id = disease.getMkb10().getId();

        DiseaseView diseaseView = new DiseaseView();
        diseaseView.setDescriptionOfTreatment("Принимать лекарства и выполнять назначения");
        diseaseView.setOnsetDateOfIllness(localDate1);
        diseaseView.setEndDateOfIllness(localDate2);
        diseaseView.setDisabilityCertificate(false);
        diseaseView.setMkb10Id(mkb10Id);

        mockMvc.perform(put("/patient/{patient_id}/disease/{disease_id}", patientId, diseaseId)
                        .content(objectMapper.writeValueAsString(diseaseView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenDiseaseWhenDeleteMedcardThenStatus200() throws Exception {
        Disease disease = createTestDisease();
        int diseaseId = disease.getId();

        mockMvc.perform(delete("/patient/disease/{disease_id}", diseaseId))
                .andExpect(status().isOk());
    }

    protected Disease createTestDisease() {
        Patient patient = new Patient();
        patient.setSecondName("Петров");
        patient.setFirstName("Петр");
        patient.setPatronymic("Петрович");
        patient.setSex(1);
        patient.setDateOfBirth(localDate);
        patient.setPoliceNumber(1111111111111111L);

        Disease disease = new Disease();
        disease.setDescriptionOfTreatment("Применять лекарства");
        disease.setOnsetDateOfIllness(localDate1);
        disease.setEndDateOfIllness(localDate2);
        disease.setDisabilityCertificate(true);
        disease.setPatient(patientService.newPatient(patient));

        int patientId = disease.getPatient().getId();
        MKB10 mkb10 = new MKB10();
        mkb10.setCode("А00");
        mkb10.setDiseaseName("Холера");
        mkb10Repository.save(mkb10);
        disease.setMkb10(mkb10);
        int mkb10Id = disease.getMkb10().getId();
        return diseaseService.newDisease(disease, patientId, mkb10Id);
    }
}
