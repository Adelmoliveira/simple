package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiariaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiariaDTO.class);
        DiariaDTO diariaDTO1 = new DiariaDTO();
        diariaDTO1.setId(1L);
        DiariaDTO diariaDTO2 = new DiariaDTO();
        assertThat(diariaDTO1).isNotEqualTo(diariaDTO2);
        diariaDTO2.setId(diariaDTO1.getId());
        assertThat(diariaDTO1).isEqualTo(diariaDTO2);
        diariaDTO2.setId(2L);
        assertThat(diariaDTO1).isNotEqualTo(diariaDTO2);
        diariaDTO1.setId(null);
        assertThat(diariaDTO1).isNotEqualTo(diariaDTO2);
    }
}
