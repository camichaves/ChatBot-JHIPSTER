package chaves.camila.repository;

import chaves.camila.domain.LineaOrden;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LineaOrden entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LineaOrdenRepository extends JpaRepository<LineaOrden, Long> {

}
