package ru.bondarenko.test.testproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name = "mkb10")
public class MKB10 {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code")
    @NotNull
    private String code;

    @Column(name = "disease_name")
    @NotNull
    private String diseaseName;

    @OneToOne(mappedBy = "mkb10", fetch = LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Disease disease;
}
