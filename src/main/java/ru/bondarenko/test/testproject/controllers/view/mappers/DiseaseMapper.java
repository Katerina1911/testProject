package ru.bondarenko.test.testproject.controllers.view.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.bondarenko.test.testproject.controllers.view.DiseaseView;
import ru.bondarenko.test.testproject.models.Disease;
import ru.bondarenko.test.testproject.services.MKB10Service;

@Component
@RequiredArgsConstructor
public class DiseaseMapper {
    private final ModelMapper modelMapper;
    private final MKB10Service mkb10Service;

    public Disease convertToDisease(DiseaseView diseaseView) {
        modelMapper.typeMap(DiseaseView.class,Disease.class).addMappings(mapper -> {
            mapper.skip(Disease::setId);
        });
        Disease disease = modelMapper.map(diseaseView, Disease.class);
        disease.setMkb10(mkb10Service.findMkb10(diseaseView.getMkb10Id()));
        return disease;
    }

    public DiseaseView convertToDiseaseView(Disease disease) {
        DiseaseView diseaseView = modelMapper.map(disease, DiseaseView.class);
        diseaseView.setMkb10Id(disease.getMkb10().getId());
        return diseaseView;
    }
}
