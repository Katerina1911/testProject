package ru.bondarenko.test.testproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bondarenko.test.testproject.models.Disease;
import ru.bondarenko.test.testproject.models.MKB10;
import ru.bondarenko.test.testproject.models.Patient;
import ru.bondarenko.test.testproject.repositories.DiseaseRepository;
import ru.bondarenko.test.testproject.services.exception.DiseaseNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiseaseService {
    private final DiseaseRepository diseaseRepository;
    private final PatientService patientService;
    private final MKB10Service mkb10Service;

    @Transactional(readOnly = true)
    public Disease findDisease(int id) {
        Optional<Disease> foundDisease = diseaseRepository.findById(id);
        return foundDisease.orElseThrow(DiseaseNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Disease> getAllDiseases(int id) {
        Patient patient = patientService.findPatient(id);
        return patient.getDiseases();
    }

    @Transactional
    public Disease newDisease(Disease disease, int patientId, int mkb10Id) {
        Patient patient = patientService.findPatient(patientId);
        disease.linkPatient(patient);
        MKB10 mkb10 = mkb10Service.findMkb10(mkb10Id);
        disease.linkMkb10(mkb10);
        return diseaseRepository.save(disease);
    }

    @Transactional
    public void updateDisease(Disease disease, int diseaseId, int patientId) {
        Disease diseaseUpdate = findDisease(diseaseId);
        diseaseUpdate.setOnsetDateOfIllness(disease.getOnsetDateOfIllness());
        diseaseUpdate.setEndDateOfIllness(disease.getEndDateOfIllness());
        diseaseUpdate.setDescriptionOfTreatment(disease.getDescriptionOfTreatment());
        diseaseUpdate.setDisabilityCertificate(disease.getDisabilityCertificate());
        diseaseUpdate.linkMkb10(disease.getMkb10());
        diseaseUpdate.linkPatient(patientService.findPatient(patientId));
    }

    @Transactional
    public void deleteDisease(int id) {
        diseaseRepository.deleteById(id);
    }
}
