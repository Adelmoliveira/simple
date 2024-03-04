package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DiariaTestSamples.*;
import static com.mycompany.myapp.domain.MissaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiariaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diaria.class);
        Diaria diaria1 = getDiariaSample1();
        Diaria diaria2 = new Diaria();
        assertThat(diaria1).isNotEqualTo(diaria2);

        diaria2.setId(diaria1.getId());
        assertThat(diaria1).isEqualTo(diaria2);

        diaria2 = getDiariaSample2();
        assertThat(diaria1).isNotEqualTo(diaria2);
    }

    @Test
    void missaoTest() throws Exception {
        Diaria diaria = getDiariaRandomSampleGenerator();
        Missao missaoBack = getMissaoRandomSampleGenerator();

        diaria.setMissao(missaoBack);
        assertThat(diaria.getMissao()).isEqualTo(missaoBack);

        diaria.missao(null);
        assertThat(diaria.getMissao()).isNull();
    }
}
