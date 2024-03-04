package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CidadesTestSamples.*;
import static com.mycompany.myapp.domain.DiariaTestSamples.*;
import static com.mycompany.myapp.domain.MissaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MissaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Missao.class);
        Missao missao1 = getMissaoSample1();
        Missao missao2 = new Missao();
        assertThat(missao1).isNotEqualTo(missao2);

        missao2.setId(missao1.getId());
        assertThat(missao1).isEqualTo(missao2);

        missao2 = getMissaoSample2();
        assertThat(missao1).isNotEqualTo(missao2);
    }

    @Test
    void municipioTest() throws Exception {
        Missao missao = getMissaoRandomSampleGenerator();
        Cidades cidadesBack = getCidadesRandomSampleGenerator();

        missao.setMunicipio(cidadesBack);
        assertThat(missao.getMunicipio()).isEqualTo(cidadesBack);

        missao.municipio(null);
        assertThat(missao.getMunicipio()).isNull();
    }

    @Test
    void diariasTest() throws Exception {
        Missao missao = getMissaoRandomSampleGenerator();
        Diaria diariaBack = getDiariaRandomSampleGenerator();

        missao.addDiarias(diariaBack);
        assertThat(missao.getDiarias()).containsOnly(diariaBack);
        assertThat(diariaBack.getMissao()).isEqualTo(missao);

        missao.removeDiarias(diariaBack);
        assertThat(missao.getDiarias()).doesNotContain(diariaBack);
        assertThat(diariaBack.getMissao()).isNull();

        missao.diarias(new HashSet<>(Set.of(diariaBack)));
        assertThat(missao.getDiarias()).containsOnly(diariaBack);
        assertThat(diariaBack.getMissao()).isEqualTo(missao);

        missao.setDiarias(new HashSet<>());
        assertThat(missao.getDiarias()).doesNotContain(diariaBack);
        assertThat(diariaBack.getMissao()).isNull();
    }
}
