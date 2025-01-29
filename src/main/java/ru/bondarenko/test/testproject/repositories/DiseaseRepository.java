package ru.bondarenko.test.testproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bondarenko.test.testproject.models.Disease;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Integer> {
    @Modifying
    @Query(value = "DELETE FROM disease WHERE id = ?1", nativeQuery = true)
    void deleteById(int id);
}
