package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServidorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServidorDTO.class);
        ServidorDTO servidorDTO1 = new ServidorDTO();
        servidorDTO1.setId(1L);
        ServidorDTO servidorDTO2 = new ServidorDTO();
        assertThat(servidorDTO1).isNotEqualTo(servidorDTO2);
        servidorDTO2.setId(servidorDTO1.getId());
        assertThat(servidorDTO1).isEqualTo(servidorDTO2);
        servidorDTO2.setId(2L);
        assertThat(servidorDTO1).isNotEqualTo(servidorDTO2);
        servidorDTO1.setId(null);
        assertThat(servidorDTO1).isNotEqualTo(servidorDTO2);
    }
}
