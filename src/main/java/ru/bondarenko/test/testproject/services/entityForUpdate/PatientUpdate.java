package ru.bondarenko.test.testproject.services.entityForUpdate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PatientUpdate {
    @Id
    private int id;

    private String secondName;

    private String firstName;

    private String patronymic;

    private int sex;

    private LocalDate dateOfBirth;

    private long policeNumber;
}
