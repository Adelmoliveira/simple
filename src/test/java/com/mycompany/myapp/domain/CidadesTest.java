package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CidadesTestSamples.*;
import static com.mycompany.myapp.domain.MissaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CidadesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cidades.class);
        Cidades cidades1 = getCidadesSample1();
        Cidades cidades2 = new Cidades();
        assertThat(cidades1).isNotEqualTo(cidades2);

        cidades2.setId(cidades1.getId());
        assertThat(cidades1).isEqualTo(cidades2);

        cidades2 = getCidadesSample2();
        assertThat(cidades1).isNotEqualTo(cidades2);
    }

    @Test
    void missaoTest() throws Exception {
        Cidades cidades = getCidadesRandomSampleGenerator();
        Missao missaoBack = getMissaoRandomSampleGenerator();

        cidades.setMissao(missaoBack);
        assertThat(cidades.getMissao()).isEqualTo(missaoBack);
        assertThat(missaoBack.getMunicipio()).isEqualTo(cidades);

        cidades.missao(null);
        assertThat(cidades.getMissao()).isNull();
        assertThat(missaoBack.getMunicipio()).isNull();
    }
}
