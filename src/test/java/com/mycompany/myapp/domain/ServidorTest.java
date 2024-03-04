package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EquipeTestSamples.*;
import static com.mycompany.myapp.domain.ServidorTestSamples.*;
import static com.mycompany.myapp.domain.SetorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServidorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servidor.class);
        Servidor servidor1 = getServidorSample1();
        Servidor servidor2 = new Servidor();
        assertThat(servidor1).isNotEqualTo(servidor2);

        servidor2.setId(servidor1.getId());
        assertThat(servidor1).isEqualTo(servidor2);

        servidor2 = getServidorSample2();
        assertThat(servidor1).isNotEqualTo(servidor2);
    }

    @Test
    void equipesTest() throws Exception {
        Servidor servidor = getServidorRandomSampleGenerator();
        Equipe equipeBack = getEquipeRandomSampleGenerator();

        servidor.addEquipes(equipeBack);
        assertThat(servidor.getEquipes()).containsOnly(equipeBack);

        servidor.removeEquipes(equipeBack);
        assertThat(servidor.getEquipes()).doesNotContain(equipeBack);

        servidor.equipes(new HashSet<>(Set.of(equipeBack)));
        assertThat(servidor.getEquipes()).containsOnly(equipeBack);

        servidor.setEquipes(new HashSet<>());
        assertThat(servidor.getEquipes()).doesNotContain(equipeBack);
    }

    @Test
    void setorTest() throws Exception {
        Servidor servidor = getServidorRandomSampleGenerator();
        Setor setorBack = getSetorRandomSampleGenerator();

        servidor.setSetor(setorBack);
        assertThat(servidor.getSetor()).isEqualTo(setorBack);
        assertThat(setorBack.getServidor()).isEqualTo(servidor);

        servidor.setor(null);
        assertThat(servidor.getSetor()).isNull();
        assertThat(setorBack.getServidor()).isNull();
    }
}
