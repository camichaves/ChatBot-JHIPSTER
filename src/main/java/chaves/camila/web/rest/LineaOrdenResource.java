package chaves.camila.web.rest;

import chaves.camila.domain.LineaOrden;
import chaves.camila.repository.LineaOrdenRepository;
import chaves.camila.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link chaves.camila.domain.LineaOrden}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LineaOrdenResource {

    private final Logger log = LoggerFactory.getLogger(LineaOrdenResource.class);

    private static final String ENTITY_NAME = "lineaOrden";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LineaOrdenRepository lineaOrdenRepository;

    public LineaOrdenResource(LineaOrdenRepository lineaOrdenRepository) {
        this.lineaOrdenRepository = lineaOrdenRepository;
    }

    /**
     * {@code POST  /linea-ordens} : Create a new lineaOrden.
     *
     * @param lineaOrden the lineaOrden to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lineaOrden, or with status {@code 400 (Bad Request)} if the lineaOrden has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/linea-ordens")
    public ResponseEntity<LineaOrden> createLineaOrden(@RequestBody LineaOrden lineaOrden) throws URISyntaxException {
        log.debug("REST request to save LineaOrden : {}", lineaOrden);
        if (lineaOrden.getId() != null) {
            throw new BadRequestAlertException("A new lineaOrden cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LineaOrden result = lineaOrdenRepository.save(lineaOrden);
        return ResponseEntity.created(new URI("/api/linea-ordens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /linea-ordens} : Updates an existing lineaOrden.
     *
     * @param lineaOrden the lineaOrden to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lineaOrden,
     * or with status {@code 400 (Bad Request)} if the lineaOrden is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lineaOrden couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/linea-ordens")
    public ResponseEntity<LineaOrden> updateLineaOrden(@RequestBody LineaOrden lineaOrden) throws URISyntaxException {
        log.debug("REST request to update LineaOrden : {}", lineaOrden);
        if (lineaOrden.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LineaOrden result = lineaOrdenRepository.save(lineaOrden);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lineaOrden.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /linea-ordens} : get all the lineaOrdens.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lineaOrdens in body.
     */
    @GetMapping("/linea-ordens")
    public List<LineaOrden> getAllLineaOrdens() {
        log.debug("REST request to get all LineaOrdens");
        return lineaOrdenRepository.findAll();
    }

    /**
     * {@code GET  /linea-ordens/:id} : get the "id" lineaOrden.
     *
     * @param id the id of the lineaOrden to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lineaOrden, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/linea-ordens/{id}")
    public ResponseEntity<LineaOrden> getLineaOrden(@PathVariable Long id) {
        log.debug("REST request to get LineaOrden : {}", id);
        Optional<LineaOrden> lineaOrden = lineaOrdenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lineaOrden);
    }

    /**
     * {@code DELETE  /linea-ordens/:id} : delete the "id" lineaOrden.
     *
     * @param id the id of the lineaOrden to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/linea-ordens/{id}")
    public ResponseEntity<Void> deleteLineaOrden(@PathVariable Long id) {
        log.debug("REST request to delete LineaOrden : {}", id);
        lineaOrdenRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
