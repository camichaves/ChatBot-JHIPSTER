package chaves.camila.web.rest;

import chaves.camila.domain.Conversacion;
import chaves.camila.repository.ConversacionRepository;
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
 * REST controller for managing {@link chaves.camila.domain.Conversacion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConversacionResource {

    private final Logger log = LoggerFactory.getLogger(ConversacionResource.class);

    private static final String ENTITY_NAME = "conversacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConversacionRepository conversacionRepository;

    public ConversacionResource(ConversacionRepository conversacionRepository) {
        this.conversacionRepository = conversacionRepository;
    }

    /**
     * {@code POST  /conversacions} : Create a new conversacion.
     *
     * @param conversacion the conversacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conversacion, or with status {@code 400 (Bad Request)} if the conversacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conversacions")
    public ResponseEntity<Conversacion> createConversacion(@RequestBody Conversacion conversacion) throws URISyntaxException {
        log.debug("REST request to save Conversacion : {}", conversacion);
        if (conversacion.getId() != null) {
            throw new BadRequestAlertException("A new conversacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Conversacion result = conversacionRepository.save(conversacion);
        return ResponseEntity.created(new URI("/api/conversacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conversacions} : Updates an existing conversacion.
     *
     * @param conversacion the conversacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conversacion,
     * or with status {@code 400 (Bad Request)} if the conversacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conversacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conversacions")
    public ResponseEntity<Conversacion> updateConversacion(@RequestBody Conversacion conversacion) throws URISyntaxException {
        log.debug("REST request to update Conversacion : {}", conversacion);
        if (conversacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Conversacion result = conversacionRepository.save(conversacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conversacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conversacions} : get all the conversacions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conversacions in body.
     */
    @GetMapping("/conversacions")
    public List<Conversacion> getAllConversacions() {
        log.debug("REST request to get all Conversacions");
        return conversacionRepository.findAll();
    }

    /**
     * {@code GET  /conversacions/:id} : get the "id" conversacion.
     *
     * @param id the id of the conversacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conversacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conversacions/{id}")
    public ResponseEntity<Conversacion> getConversacion(@PathVariable Long id) {
        log.debug("REST request to get Conversacion : {}", id);
        Optional<Conversacion> conversacion = conversacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conversacion);
    }

    /**
     * {@code DELETE  /conversacions/:id} : delete the "id" conversacion.
     *
     * @param id the id of the conversacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conversacions/{id}")
    public ResponseEntity<Void> deleteConversacion(@PathVariable Long id) {
        log.debug("REST request to delete Conversacion : {}", id);
        conversacionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
