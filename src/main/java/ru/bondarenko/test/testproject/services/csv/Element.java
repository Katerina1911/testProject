package ru.bondarenko.test.testproject.services.csv;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.Date;

@JsonPropertyOrder({"column1", "column2", "codeColumn", "diseaseColumn", "column5", "column6", "column7", "column8"})
public class Element {
    public int column1;
    public String column2;
    @Getter
    public String codeColumn;
    @Getter
    public String diseaseColumn;
    public int column5;
    public int column6;
    public int column7;
    @JsonFormat(pattern = "yyyy.MM.dd")
    public Date column8;
}
