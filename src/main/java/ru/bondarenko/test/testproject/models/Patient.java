package ru.bondarenko.test.testproject.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name = "Patient")
public class Patient {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "second_name")
    @NotBlank(message = "Фамилия не должна быть пустой")
    @Pattern(regexp = "[-а-яА-ЯёЁ\\s]+$")
    @Size(min = 2, max = 100, message = "Фамилия должна быть от 2 до 100 символов длиной")
    private String secondName;

    @Column(name = "first_name")
    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Pattern(regexp = "[-а-яА-ЯёЁ\\s]+$")
    private String firstName;

    @Column(name = "patronymic")
    @Size(min = 2, max = 100, message = "Отчество должно быть от 2 до 100 символов длиной")
    @Pattern(regexp = "[-а-яА-ЯёЁ\\s]+$")
    private String patronymic;

    @Column(name = "sex")
    @NotNull
    @Min(1)
    @Max(2)
    private int sex;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "Дата рождения не может быть в будущем")
    @Schema(description = "Дата рождения пациента", example = "1970-01-01")
    @NotNull
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "police_number")
    @Positive
    @NotNull
    @Digits(integer = 16, fraction = 0, message = "Номер полиса должен состоять из 16 цифр")
    private long policeNumber;

    @OneToMany(mappedBy = "patient", fetch = LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Disease> diseases;
}
