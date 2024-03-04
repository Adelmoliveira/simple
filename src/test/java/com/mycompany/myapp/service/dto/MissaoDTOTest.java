package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MissaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MissaoDTO.class);
        MissaoDTO missaoDTO1 = new MissaoDTO();
        missaoDTO1.setId(1L);
        MissaoDTO missaoDTO2 = new MissaoDTO();
        assertThat(missaoDTO1).isNotEqualTo(missaoDTO2);
        missaoDTO2.setId(missaoDTO1.getId());
        assertThat(missaoDTO1).isEqualTo(missaoDTO2);
        missaoDTO2.setId(2L);
        assertThat(missaoDTO1).isNotEqualTo(missaoDTO2);
        missaoDTO1.setId(null);
        assertThat(missaoDTO1).isNotEqualTo(missaoDTO2);
    }
}
