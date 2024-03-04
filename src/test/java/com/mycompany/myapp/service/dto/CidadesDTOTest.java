package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CidadesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CidadesDTO.class);
        CidadesDTO cidadesDTO1 = new CidadesDTO();
        cidadesDTO1.setId(1L);
        CidadesDTO cidadesDTO2 = new CidadesDTO();
        assertThat(cidadesDTO1).isNotEqualTo(cidadesDTO2);
        cidadesDTO2.setId(cidadesDTO1.getId());
        assertThat(cidadesDTO1).isEqualTo(cidadesDTO2);
        cidadesDTO2.setId(2L);
        assertThat(cidadesDTO1).isNotEqualTo(cidadesDTO2);
        cidadesDTO1.setId(null);
        assertThat(cidadesDTO1).isNotEqualTo(cidadesDTO2);
    }
}
