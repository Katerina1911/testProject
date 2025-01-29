package ru.bondarenko.test.testproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bondarenko.test.testproject.models.MKB10;

@Repository
public interface MKB10Repository extends JpaRepository<MKB10, Integer> {
    MKB10 findByCode(String code);

    boolean existsByCode(String code);

    @Override
    void delete(MKB10 entity);
}
