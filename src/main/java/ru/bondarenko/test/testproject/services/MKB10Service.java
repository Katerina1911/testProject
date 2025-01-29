package ru.bondarenko.test.testproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bondarenko.test.testproject.models.MKB10;
import ru.bondarenko.test.testproject.repositories.MKB10Repository;
import ru.bondarenko.test.testproject.services.exception.CodeMkb10NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MKB10Service {
    private final MKB10Repository mkb10Repository;

    @Transactional(readOnly = true)
    @Cacheable("mkb10")
    public MKB10 findMkb10(int id) {
        Optional<MKB10> foundMkb10 = mkb10Repository.findById(id);
        return foundMkb10.orElseThrow(CodeMkb10NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<MKB10> findAllMkb10() {
        return mkb10Repository.findAll();
    }

    @Transactional
    public MKB10 newMkb10(MKB10 mkb10) {
        return mkb10Repository.save(mkb10);
    }

    @Transactional(readOnly = true)
    public boolean isExistMkb10 (String code) {
        return mkb10Repository.existsByCode(code);
    }

    @Transactional(readOnly = true)
    public MKB10 findMkb10ByCode(String code) {
        return mkb10Repository.findByCode(code);
    }
}
