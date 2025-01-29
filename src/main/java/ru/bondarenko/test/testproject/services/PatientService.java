package ru.bondarenko.test.testproject.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bondarenko.test.testproject.models.Patient;
import ru.bondarenko.test.testproject.repositories.PatientRepository;
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
        Patient updatePatient = findPatient(id);
        updatePatient.setSecondName(patient.getSecondName());
        updatePatient.setFirstName(patient.getFirstName());
        updatePatient.setPatronymic(patient.getPatronymic());
        updatePatient.setSex(patient.getSex());
        updatePatient.setDateOfBirth(patient.getDateOfBirth());
        updatePatient.setPoliceNumber(patient.getPoliceNumber());
    }

    @Transactional
    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }
}
