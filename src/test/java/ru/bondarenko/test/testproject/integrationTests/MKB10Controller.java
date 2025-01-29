package ru.bondarenko.test.testproject.integrationTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.bondarenko.test.testproject.models.MKB10;
import ru.bondarenko.test.testproject.repositories.MKB10Repository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MKB10Controller {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MKB10Repository mkb10Repository;

    @Test
    void whenGetAllMKB10Status200() throws Exception {
        MKB10 mkb10 = new MKB10();
        mkb10.setCode("А00");
        mkb10.setDiseaseName("Холера");
        mkb10Repository.save(mkb10);

        mockMvc.perform(get("/dictionary/{id}", mkb10.getId()))
                .andExpect(status().isOk());
    }
}
