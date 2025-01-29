package ru.bondarenko.test.testproject.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bondarenko.test.testproject.models.Patient;
import ru.bondarenko.test.testproject.repositories.PatientRepository;
import ru.bondarenko.test.testproject.services.entityForUpdate.PatientUpdate;
import ru.bondarenko.test.testproject.services.exception.PatientNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Patient findPatient(int id) {
        Optional<Patient> foundPatient = patientRepository.findById(id);
        return foundPatient.orElseThrow(PatientNotFoundException::new);
    }

    @Transactional
    public Patient newPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Transactional
    public void updatePatient(Patient patient, int id) {
        PatientUpdate updatePatient = mapToPatientUpdate(patient);
        updatePatient.setId(id);
        patientRepository.save(convertToPatient(updatePatient));
    }

    @Transactional
    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }

    public PatientUpdate mapToPatientUpdate(Patient patient) {
        return modelMapper.map(patient, PatientUpdate.class);
    }

    public Patient convertToPatient(PatientUpdate patientUpdate) {
        return modelMapper.map(patientUpdate, Patient.class);
    }
}
