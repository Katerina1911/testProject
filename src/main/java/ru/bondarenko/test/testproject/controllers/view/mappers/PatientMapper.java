package ru.bondarenko.test.testproject.controllers.view.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.bondarenko.test.testproject.controllers.view.PatientView;
import ru.bondarenko.test.testproject.models.Patient;

@Component
@RequiredArgsConstructor
public class PatientMapper {
    private final ModelMapper modelMapper;

    public Patient convertToPatient(PatientView patientView) {
        return modelMapper.map(patientView, Patient.class);
    }

    public PatientView convertToPatientView(Patient patient) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(PatientView.class,Patient.class).addMappings(mapper -> {
            mapper.skip(Patient::setDiseases);
        });
        return modelMapper.map(patient, PatientView.class);
    }
}
