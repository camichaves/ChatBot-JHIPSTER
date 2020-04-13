package chaves.camila.web.rest;

import chaves.camila.domain.Agente;
import chaves.camila.repository.AgenteRepository;
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
 * REST controller for managing {@link chaves.camila.domain.Agente}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AgenteResource {

    private final Logger log = LoggerFactory.getLogger(AgenteResource.class);

    private static final String ENTITY_NAME = "agente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgenteRepository agenteRepository;

    public AgenteResource(AgenteRepository agenteRepository) {
        this.agenteRepository = agenteRepository;
    }

    /**
     * {@code POST  /agentes} : Create a new agente.
     *
     * @param agente the agente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agente, or with status {@code 400 (Bad Request)} if the agente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agentes")
    public ResponseEntity<Agente> createAgente(@RequestBody Agente agente) throws URISyntaxException {
        log.debug("REST request to save Agente : {}", agente);
        if (agente.getId() != null) {
            throw new BadRequestAlertException("A new agente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agente result = agenteRepository.save(agente);
        return ResponseEntity.created(new URI("/api/agentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agentes} : Updates an existing agente.
     *
     * @param agente the agente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agente,
     * or with status {@code 400 (Bad Request)} if the agente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agentes")
    public ResponseEntity<Agente> updateAgente(@RequestBody Agente agente) throws URISyntaxException {
        log.debug("REST request to update Agente : {}", agente);
        if (agente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Agente result = agenteRepository.save(agente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agentes} : get all the agentes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agentes in body.
     */
    @GetMapping("/agentes")
    public List<Agente> getAllAgentes() {
        log.debug("REST request to get all Agentes");
        return agenteRepository.findAll();
    }

    /**
     * {@code GET  /agentes/:id} : get the "id" agente.
     *
     * @param id the id of the agente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agentes/{id}")
    public ResponseEntity<Agente> getAgente(@PathVariable Long id) {
        log.debug("REST request to get Agente : {}", id);
        Optional<Agente> agente = agenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(agente);
    }

    /**
     * {@code DELETE  /agentes/:id} : delete the "id" agente.
     *
     * @param id the id of the agente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agentes/{id}")
    public ResponseEntity<Void> deleteAgente(@PathVariable Long id) {
        log.debug("REST request to delete Agente : {}", id);
        agenteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
