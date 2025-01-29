package ru.bondarenko.test.testproject.services.csv;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.bondarenko.test.testproject.models.MKB10;
import ru.bondarenko.test.testproject.services.MKB10Service;
import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class UpdateMkb10 {
    private final MKB10Service mkb10Service;
    private final Mkb10Parser mkb10Parser;

    @CacheEvict("mkb10")
    @Scheduled(cron = "${time-for-update-mkb10}")
    public void fixedRateUpdateMkb10() {
        List<Element> elements = mkb10Parser.parseDictionary();

        for (Element element : elements) {
            if (!element.getCodeColumn().isEmpty() && !element.getDiseaseColumn().isEmpty()) {
                String code = element.getCodeColumn();
                String diseaseName = element.getDiseaseColumn();
                MKB10 mkb10 = new MKB10();
                if (mkb10Service.isExistMkb10(code)) {
                    mkb10 = mkb10Service.findMkb10ByCode(code);
                } else {
                    mkb10.setCode(code);
                    mkb10.setDiseaseName(diseaseName);
                }
                mkb10Service.newMkb10(mkb10);
            }
        }
    }
}
