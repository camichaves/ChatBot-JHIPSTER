package chaves.camila.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import chaves.camila.web.rest.TestUtil;

public class LineaOrdenTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineaOrden.class);
        LineaOrden lineaOrden1 = new LineaOrden();
        lineaOrden1.setId(1L);
        LineaOrden lineaOrden2 = new LineaOrden();
        lineaOrden2.setId(lineaOrden1.getId());
        assertThat(lineaOrden1).isEqualTo(lineaOrden2);
        lineaOrden2.setId(2L);
        assertThat(lineaOrden1).isNotEqualTo(lineaOrden2);
        lineaOrden1.setId(null);
        assertThat(lineaOrden1).isNotEqualTo(lineaOrden2);
    }
}
