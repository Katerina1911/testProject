package ru.bondarenko.test.testproject.services.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class Mkb10CsvParser implements Mkb10Parser {
    @Value("${mkb10.url}")
    private String url;

    public List<Element> parseDictionary() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String response = restTemplate.getForObject(url, String.class);

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Element.class)
                .withColumnSeparator(',').withSkipFirstDataRow(true);
        MappingIterator<Element> iterator;
        try {
            iterator = mapper
                    .readerFor(Element.class)
                    .with(schema)
                    .readValues(response);
            List<Element> elements;
            elements = iterator.readAll();
            return elements;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
