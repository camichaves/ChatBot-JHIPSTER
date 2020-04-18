package chaves.camila.web.rest;

import chaves.camila.EdgarBotApp;
import chaves.camila.domain.Agente;
import chaves.camila.repository.AgenteRepository;
import chaves.camila.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static chaves.camila.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AgenteResource} REST controller.
 */
@SpringBootTest(classes = EdgarBotApp.class)
public class AgenteResourceIT {

    private static final String DEFAULT_AUTHOR_WA = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_WA = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPONIBLE = false;
    private static final Boolean UPDATED_DISPONIBLE = true;

    private static final String DEFAULT_HORARIO = "AAAAAAAAAA";
    private static final String UPDATED_HORARIO = "BBBBBBBBBB";

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAgenteMockMvc;

    private Agente agente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgenteResource agenteResource = new AgenteResource(agenteRepository);
        this.restAgenteMockMvc = MockMvcBuilders.standaloneSetup(agenteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agente createEntity(EntityManager em) {
        Agente agente = new Agente()
            .authorWA(DEFAULT_AUTHOR_WA)
            .nombre(DEFAULT_NOMBRE)
            .disponible(DEFAULT_DISPONIBLE)
            .horario(DEFAULT_HORARIO);
        return agente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agente createUpdatedEntity(EntityManager em) {
        Agente agente = new Agente()
            .authorWA(UPDATED_AUTHOR_WA)
            .nombre(UPDATED_NOMBRE)
            .disponible(UPDATED_DISPONIBLE)
            .horario(UPDATED_HORARIO);
        return agente;
    }

    @BeforeEach
    public void initTest() {
        agente = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgente() throws Exception {
        int databaseSizeBeforeCreate = agenteRepository.findAll().size();

        // Create the Agente
        restAgenteMockMvc.perform(post("/api/agentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agente)))
            .andExpect(status().isCreated());

        // Validate the Agente in the database
        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeCreate + 1);
        Agente testAgente = agenteList.get(agenteList.size() - 1);
        assertThat(testAgente.getAuthorWA()).isEqualTo(DEFAULT_AUTHOR_WA);
        assertThat(testAgente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAgente.isDisponible()).isEqualTo(DEFAULT_DISPONIBLE);
        assertThat(testAgente.getHorario()).isEqualTo(DEFAULT_HORARIO);
    }

    @Test
    @Transactional
    public void createAgenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agenteRepository.findAll().size();

        // Create the Agente with an existing ID
        agente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgenteMockMvc.perform(post("/api/agentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agente)))
            .andExpect(status().isBadRequest());

        // Validate the Agente in the database
        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAgentes() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList
        restAgenteMockMvc.perform(get("/api/agentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agente.getId().intValue())))
            .andExpect(jsonPath("$.[*].authorWA").value(hasItem(DEFAULT_AUTHOR_WA)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].disponible").value(hasItem(DEFAULT_DISPONIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO)));
    }
    
    @Test
    @Transactional
    public void getAgente() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get the agente
        restAgenteMockMvc.perform(get("/api/agentes/{id}", agente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agente.getId().intValue()))
            .andExpect(jsonPath("$.authorWA").value(DEFAULT_AUTHOR_WA))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.disponible").value(DEFAULT_DISPONIBLE.booleanValue()))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO));
    }

    @Test
    @Transactional
    public void getNonExistingAgente() throws Exception {
        // Get the agente
        restAgenteMockMvc.perform(get("/api/agentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgente() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        int databaseSizeBeforeUpdate = agenteRepository.findAll().size();

        // Update the agente
        Agente updatedAgente = agenteRepository.findById(agente.getId()).get();
        // Disconnect from session so that the updates on updatedAgente are not directly saved in db
        em.detach(updatedAgente);
        updatedAgente
            .authorWA(UPDATED_AUTHOR_WA)
            .nombre(UPDATED_NOMBRE)
            .disponible(UPDATED_DISPONIBLE)
            .horario(UPDATED_HORARIO);

        restAgenteMockMvc.perform(put("/api/agentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgente)))
            .andExpect(status().isOk());

        // Validate the Agente in the database
        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeUpdate);
        Agente testAgente = agenteList.get(agenteList.size() - 1);
        assertThat(testAgente.getAuthorWA()).isEqualTo(UPDATED_AUTHOR_WA);
        assertThat(testAgente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAgente.isDisponible()).isEqualTo(UPDATED_DISPONIBLE);
        assertThat(testAgente.getHorario()).isEqualTo(UPDATED_HORARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingAgente() throws Exception {
        int databaseSizeBeforeUpdate = agenteRepository.findAll().size();

        // Create the Agente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenteMockMvc.perform(put("/api/agentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agente)))
            .andExpect(status().isBadRequest());

        // Validate the Agente in the database
        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgente() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        int databaseSizeBeforeDelete = agenteRepository.findAll().size();

        // Delete the agente
        restAgenteMockMvc.perform(delete("/api/agentes/{id}", agente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
