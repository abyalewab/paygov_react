package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaygovTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paygov.class);
        Paygov paygov1 = new Paygov();
        paygov1.setId(1L);
        Paygov paygov2 = new Paygov();
        paygov2.setId(paygov1.getId());
        assertThat(paygov1).isEqualTo(paygov2);
        paygov2.setId(2L);
        assertThat(paygov1).isNotEqualTo(paygov2);
        paygov1.setId(null);
        assertThat(paygov1).isNotEqualTo(paygov2);
    }
}
