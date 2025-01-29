package ru.bondarenko.test.testproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "disease")
public class Disease {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "onset_date_of_illness")
    @PastOrPresent(message = "Дата начала болезни не может быть в будущем")
    @NotNull
    private LocalDate onsetDateOfIllness;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "end_date_of_illness")
    @PastOrPresent(message = "Дата окончания болезни не может быть в будущем")
    @NotNull
    private LocalDate endDateOfIllness;

    @Column(name = "description_of_treatment")
    @NotBlank(message = "Назначения не должны быть пустыми")
    @Size(min = 1, max = 1024, message = "Назначения должны быть не более 1024 символов")
    private String descriptionOfTreatment;

    @Column(name = "disability_certificate")
    @NotNull
    private Boolean disabilityCertificate;

    @OneToOne
    @JoinColumn(name = "mkb10_id", referencedColumnName = "id")
    private MKB10 mkb10;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    public void linkPatient(Patient patient) {
        this.patient = patient;
        patient.getDiseases().add(this);
    }

    public void linkMkb10(MKB10 mkb10) {
        this.mkb10 = mkb10;
        mkb10.setDisease(this);
    }
}
