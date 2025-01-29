package ru.bondarenko.test.testproject.controllers.view.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.bondarenko.test.testproject.controllers.view.MKB10View;
import ru.bondarenko.test.testproject.models.MKB10;

@Component
@RequiredArgsConstructor
public class MKB10Mapper {
    private final ModelMapper modelMapper;

    public MKB10 convertToMKB10(MKB10View mkb10View) {
        return modelMapper.map(mkb10View, MKB10.class);
    }

    public MKB10View convertToMkb10View(MKB10 mkb10) {
        return modelMapper.map(mkb10, MKB10View.class);
    }
}
