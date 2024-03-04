package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ServidorTestSamples.*;
import static com.mycompany.myapp.domain.SetorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SetorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Setor.class);
        Setor setor1 = getSetorSample1();
        Setor setor2 = new Setor();
        assertThat(setor1).isNotEqualTo(setor2);

        setor2.setId(setor1.getId());
        assertThat(setor1).isEqualTo(setor2);

        setor2 = getSetorSample2();
        assertThat(setor1).isNotEqualTo(setor2);
    }

    @Test
    void servidorTest() throws Exception {
        Setor setor = getSetorRandomSampleGenerator();
        Servidor servidorBack = getServidorRandomSampleGenerator();

        setor.setServidor(servidorBack);
        assertThat(setor.getServidor()).isEqualTo(servidorBack);

        setor.servidor(null);
        assertThat(setor.getServidor()).isNull();
    }
}
