package ru.bondarenko.test.testproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bondarenko.test.testproject.models.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    @Modifying
    @Query(value = "DELETE FROM patient WHERE id = ?1", nativeQuery = true)
    void deleteById(int id);
}
