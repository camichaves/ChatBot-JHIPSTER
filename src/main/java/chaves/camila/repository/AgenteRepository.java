package chaves.camila.repository;

import chaves.camila.domain.Agente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Agente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {

}
