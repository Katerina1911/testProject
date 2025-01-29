package ru.bondarenko.test.testproject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.bondarenko.test.testproject.models.MKB10;
import ru.bondarenko.test.testproject.repositories.MKB10Repository;
import ru.bondarenko.test.testproject.services.MKB10Service;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CacheTest {
    @Autowired
    MKB10Service mkb10Service;
    @Autowired
    MKB10Repository mkb10Repository;
    @PersistenceContext(name = "ru.bondarenko.test.testproject")
    private EntityManager entityManager;

    @Test
    @Transactional
    public void getCache() {
        MKB10 mkb10 = new MKB10();
        mkb10.setCode("А00");
        mkb10.setDiseaseName("Холера");
        int id = mkb10Repository.save(mkb10).getId();
        MKB10 mkb = mkb10Service.findMkb10(id);
        mkb10Repository.delete(mkb);
        entityManager.flush();
        assertNotNull(mkb10Service.findMkb10(id));
    }
}
