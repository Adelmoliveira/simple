package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EquipeTestSamples.*;
import static com.mycompany.myapp.domain.ServidorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EquipeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipe.class);
        Equipe equipe1 = getEquipeSample1();
        Equipe equipe2 = new Equipe();
        assertThat(equipe1).isNotEqualTo(equipe2);

        equipe2.setId(equipe1.getId());
        assertThat(equipe1).isEqualTo(equipe2);

        equipe2 = getEquipeSample2();
        assertThat(equipe1).isNotEqualTo(equipe2);
    }

    @Test
    void servidoresTest() throws Exception {
        Equipe equipe = getEquipeRandomSampleGenerator();
        Servidor servidorBack = getServidorRandomSampleGenerator();

        equipe.addServidores(servidorBack);
        assertThat(equipe.getServidores()).containsOnly(servidorBack);
        assertThat(servidorBack.getEquipes()).containsOnly(equipe);

        equipe.removeServidores(servidorBack);
        assertThat(equipe.getServidores()).doesNotContain(servidorBack);
        assertThat(servidorBack.getEquipes()).doesNotContain(equipe);

        equipe.servidores(new HashSet<>(Set.of(servidorBack)));
        assertThat(equipe.getServidores()).containsOnly(servidorBack);
        assertThat(servidorBack.getEquipes()).containsOnly(equipe);

        equipe.setServidores(new HashSet<>());
        assertThat(equipe.getServidores()).doesNotContain(servidorBack);
        assertThat(servidorBack.getEquipes()).doesNotContain(equipe);
    }
}
