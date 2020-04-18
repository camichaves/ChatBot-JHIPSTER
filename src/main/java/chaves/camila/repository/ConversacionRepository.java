package chaves.camila.repository;

import chaves.camila.domain.Conversacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Conversacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Long> {

}
