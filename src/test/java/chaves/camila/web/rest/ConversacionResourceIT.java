package chaves.camila.web.rest;

import chaves.camila.ChatBotApp;
import chaves.camila.domain.Conversacion;
import chaves.camila.repository.ConversacionRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static chaves.camila.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConversacionResource} REST controller.
 */
@SpringBootTest(classes = ChatBotApp.class)
public class ConversacionResourceIT {

    private static final String DEFAULT_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_SESSION_DF = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_DF = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR_WA = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_WA = "BBBBBBBBBB";

    private static final Instant DEFAULT_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ULT_ACT_CLI = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ULT_ACT_CLI = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ConversacionRepository conversacionRepository;

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

    private MockMvc restConversacionMockMvc;

    private Conversacion conversacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConversacionResource conversacionResource = new ConversacionResource(conversacionRepository);
        this.restConversacionMockMvc = MockMvcBuilders.standaloneSetup(conversacionResource)
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
    public static Conversacion createEntity(EntityManager em) {
        Conversacion conversacion = new Conversacion()
            .cliente(DEFAULT_CLIENTE)
            .sessionDF(DEFAULT_SESSION_DF)
            .authorWA(DEFAULT_AUTHOR_WA)
            .inicio(DEFAULT_INICIO)
            .ultActCli(DEFAULT_ULT_ACT_CLI)
            .fin(DEFAULT_FIN);
        return conversacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conversacion createUpdatedEntity(EntityManager em) {
        Conversacion conversacion = new Conversacion()
            .cliente(UPDATED_CLIENTE)
            .sessionDF(UPDATED_SESSION_DF)
            .authorWA(UPDATED_AUTHOR_WA)
            .inicio(UPDATED_INICIO)
            .ultActCli(UPDATED_ULT_ACT_CLI)
            .fin(UPDATED_FIN);
        return conversacion;
    }

    @BeforeEach
    public void initTest() {
        conversacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createConversacion() throws Exception {
        int databaseSizeBeforeCreate = conversacionRepository.findAll().size();

        // Create the Conversacion
        restConversacionMockMvc.perform(post("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversacion)))
            .andExpect(status().isCreated());

        // Validate the Conversacion in the database
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeCreate + 1);
        Conversacion testConversacion = conversacionList.get(conversacionList.size() - 1);
        assertThat(testConversacion.getCliente()).isEqualTo(DEFAULT_CLIENTE);
        assertThat(testConversacion.getSessionDF()).isEqualTo(DEFAULT_SESSION_DF);
        assertThat(testConversacion.getAuthorWA()).isEqualTo(DEFAULT_AUTHOR_WA);
        assertThat(testConversacion.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testConversacion.getUltActCli()).isEqualTo(DEFAULT_ULT_ACT_CLI);
        assertThat(testConversacion.getFin()).isEqualTo(DEFAULT_FIN);
    }

    @Test
    @Transactional
    public void createConversacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conversacionRepository.findAll().size();

        // Create the Conversacion with an existing ID
        conversacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConversacionMockMvc.perform(post("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversacion)))
            .andExpect(status().isBadRequest());

        // Validate the Conversacion in the database
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConversacions() throws Exception {
        // Initialize the database
        conversacionRepository.saveAndFlush(conversacion);

        // Get all the conversacionList
        restConversacionMockMvc.perform(get("/api/conversacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE)))
            .andExpect(jsonPath("$.[*].sessionDF").value(hasItem(DEFAULT_SESSION_DF)))
            .andExpect(jsonPath("$.[*].authorWA").value(hasItem(DEFAULT_AUTHOR_WA)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].ultActCli").value(hasItem(DEFAULT_ULT_ACT_CLI.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())));
    }
    
    @Test
    @Transactional
    public void getConversacion() throws Exception {
        // Initialize the database
        conversacionRepository.saveAndFlush(conversacion);

        // Get the conversacion
        restConversacionMockMvc.perform(get("/api/conversacions/{id}", conversacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conversacion.getId().intValue()))
            .andExpect(jsonPath("$.cliente").value(DEFAULT_CLIENTE))
            .andExpect(jsonPath("$.sessionDF").value(DEFAULT_SESSION_DF))
            .andExpect(jsonPath("$.authorWA").value(DEFAULT_AUTHOR_WA))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.ultActCli").value(DEFAULT_ULT_ACT_CLI.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConversacion() throws Exception {
        // Get the conversacion
        restConversacionMockMvc.perform(get("/api/conversacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConversacion() throws Exception {
        // Initialize the database
        conversacionRepository.saveAndFlush(conversacion);

        int databaseSizeBeforeUpdate = conversacionRepository.findAll().size();

        // Update the conversacion
        Conversacion updatedConversacion = conversacionRepository.findById(conversacion.getId()).get();
        // Disconnect from session so that the updates on updatedConversacion are not directly saved in db
        em.detach(updatedConversacion);
        updatedConversacion
            .cliente(UPDATED_CLIENTE)
            .sessionDF(UPDATED_SESSION_DF)
            .authorWA(UPDATED_AUTHOR_WA)
            .inicio(UPDATED_INICIO)
            .ultActCli(UPDATED_ULT_ACT_CLI)
            .fin(UPDATED_FIN);

        restConversacionMockMvc.perform(put("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConversacion)))
            .andExpect(status().isOk());

        // Validate the Conversacion in the database
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeUpdate);
        Conversacion testConversacion = conversacionList.get(conversacionList.size() - 1);
        assertThat(testConversacion.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testConversacion.getSessionDF()).isEqualTo(UPDATED_SESSION_DF);
        assertThat(testConversacion.getAuthorWA()).isEqualTo(UPDATED_AUTHOR_WA);
        assertThat(testConversacion.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testConversacion.getUltActCli()).isEqualTo(UPDATED_ULT_ACT_CLI);
        assertThat(testConversacion.getFin()).isEqualTo(UPDATED_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingConversacion() throws Exception {
        int databaseSizeBeforeUpdate = conversacionRepository.findAll().size();

        // Create the Conversacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConversacionMockMvc.perform(put("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversacion)))
            .andExpect(status().isBadRequest());

        // Validate the Conversacion in the database
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConversacion() throws Exception {
        // Initialize the database
        conversacionRepository.saveAndFlush(conversacion);

        int databaseSizeBeforeDelete = conversacionRepository.findAll().size();

        // Delete the conversacion
        restConversacionMockMvc.perform(delete("/api/conversacions/{id}", conversacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
