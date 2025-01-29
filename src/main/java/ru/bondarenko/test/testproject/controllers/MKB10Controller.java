package ru.bondarenko.test.testproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.bondarenko.test.testproject.controllers.view.MKB10View;
import ru.bondarenko.test.testproject.controllers.view.mappers.MKB10Mapper;
import ru.bondarenko.test.testproject.services.MKB10Service;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Справочник кодов mkb10", description = "Содержит перечень заболеваний и кодов mkb10")
@RequiredArgsConstructor
public class MKB10Controller {
    private final MKB10Service mkb10Service;
    private final MKB10Mapper mkb10Mapper;

    @Operation(summary = "Поиск кода по ID")
    @GetMapping("/dictionary/{id}")
    public MKB10View getCodeMkb10(@PathVariable("id") int id) {
        return mkb10Mapper.convertToMkb10View(mkb10Service.findMkb10(id));
    }

    @Operation(
            summary = "Справочник кодов mkb10",
            description = "Позволяет просмотреть весь справочник"
    )
    @GetMapping("/dictionary")
    public List<MKB10View> getAllMkb10() {
        return mkb10Service.findAllMkb10().stream().map(mkb10Mapper::convertToMkb10View)
                .collect(Collectors.toList());
    }
}
