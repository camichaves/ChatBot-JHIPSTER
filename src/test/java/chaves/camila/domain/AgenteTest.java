package chaves.camila.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import chaves.camila.web.rest.TestUtil;

public class AgenteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agente.class);
        Agente agente1 = new Agente();
        agente1.setId(1L);
        Agente agente2 = new Agente();
        agente2.setId(agente1.getId());
        assertThat(agente1).isEqualTo(agente2);
        agente2.setId(2L);
        assertThat(agente1).isNotEqualTo(agente2);
        agente1.setId(null);
        assertThat(agente1).isNotEqualTo(agente2);
    }
}
