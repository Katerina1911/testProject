package ru.bondarenko.test.testproject.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bondarenko.test.testproject.controllers.view.PatientView;
import ru.bondarenko.test.testproject.models.Patient;
import ru.bondarenko.test.testproject.services.PatientService;

import java.time.LocalDate;
import java.time.ZoneId;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PatientController {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PatientService patientService;

    final java.util.Date date = new java.util.Date(1970 - 01 - 01);
    final LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    @Test
    void givenPatientWhenAddThenStatus201() throws Exception {
        Patient patient = createTestPatient();

        mockMvc.perform(post("/patient")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void givenIdWhenGetExistingPatientThenStatus200andPatientReturned() throws Exception {
        int id = createTestPatient().getId();

        mockMvc.perform(get("/patient/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.secondName").value("Петров"))
                .andExpect(jsonPath("$.firstName").value("Петр"))
                .andExpect(jsonPath("$.patronymic").value("Петрович"))
                .andExpect(jsonPath("$.sex").value(1))
                .andExpect(jsonPath("$.dateOfBirth").value("1970-01-01"))
                .andExpect(jsonPath("$.policeNumber").value(1111111111111111L));
    }

    @Test
    void givePatientWhenUpdateThenStatus200() throws Exception {
        int id = createTestPatient().getId();

        PatientView patientView = new PatientView();
        patientView.setSecondName("Сидоров");
        patientView.setFirstName("Петр");
        patientView.setPatronymic("Петрович");
        patientView.setSex(1);
        patientView.setDateOfBirth(localDate);
        patientView.setPoliceNumber(2111111111111111L);

        mockMvc.perform(put("/patient/{id}", id)
                        .content(objectMapper.writeValueAsString(patientView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenPatientWhenDeletePatientThenStatus200() throws Exception {
        Patient patient = createTestPatient();

        mockMvc.perform(delete("/patient/{id}", patient.getId()))
                .andExpect(status().isOk());
    }

    protected Patient createTestPatient() {
        Patient patient = new Patient();
        patient.setSecondName("Петров");
        patient.setFirstName("Петр");
        patient.setPatronymic("Петрович");
        patient.setSex(1);
        patient.setDateOfBirth(localDate);
        patient.setPoliceNumber(1111111111111111L);
        return patientService.newPatient(patient);
    }
}
