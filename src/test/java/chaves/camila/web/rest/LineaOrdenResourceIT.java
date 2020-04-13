package chaves.camila.web.rest;

import chaves.camila.ChatBotApp;
import chaves.camila.domain.LineaOrden;
import chaves.camila.repository.LineaOrdenRepository;
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
 * Integration tests for the {@link LineaOrdenResource} REST controller.
 */
@SpringBootTest(classes = ChatBotApp.class)
public class LineaOrdenResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DETALLE = "AAAAAAAAAA";
    private static final String UPDATED_DETALLE = "BBBBBBBBBB";

    private static final Long DEFAULT_CANTIDAD = 1L;
    private static final Long UPDATED_CANTIDAD = 2L;

    private static final Long DEFAULT_PRECIO = 1L;
    private static final Long UPDATED_PRECIO = 2L;

    @Autowired
    private LineaOrdenRepository lineaOrdenRepository;

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

    private MockMvc restLineaOrdenMockMvc;

    private LineaOrden lineaOrden;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LineaOrdenResource lineaOrdenResource = new LineaOrdenResource(lineaOrdenRepository);
        this.restLineaOrdenMockMvc = MockMvcBuilders.standaloneSetup(lineaOrdenResource)
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
    public static LineaOrden createEntity(EntityManager em) {
        LineaOrden lineaOrden = new LineaOrden()
            .codigo(DEFAULT_CODIGO)
            .detalle(DEFAULT_DETALLE)
            .cantidad(DEFAULT_CANTIDAD)
            .precio(DEFAULT_PRECIO);
        return lineaOrden;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LineaOrden createUpdatedEntity(EntityManager em) {
        LineaOrden lineaOrden = new LineaOrden()
            .codigo(UPDATED_CODIGO)
            .detalle(UPDATED_DETALLE)
            .cantidad(UPDATED_CANTIDAD)
            .precio(UPDATED_PRECIO);
        return lineaOrden;
    }

    @BeforeEach
    public void initTest() {
        lineaOrden = createEntity(em);
    }

    @Test
    @Transactional
    public void createLineaOrden() throws Exception {
        int databaseSizeBeforeCreate = lineaOrdenRepository.findAll().size();

        // Create the LineaOrden
        restLineaOrdenMockMvc.perform(post("/api/linea-ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineaOrden)))
            .andExpect(status().isCreated());

        // Validate the LineaOrden in the database
        List<LineaOrden> lineaOrdenList = lineaOrdenRepository.findAll();
        assertThat(lineaOrdenList).hasSize(databaseSizeBeforeCreate + 1);
        LineaOrden testLineaOrden = lineaOrdenList.get(lineaOrdenList.size() - 1);
        assertThat(testLineaOrden.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testLineaOrden.getDetalle()).isEqualTo(DEFAULT_DETALLE);
        assertThat(testLineaOrden.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testLineaOrden.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createLineaOrdenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lineaOrdenRepository.findAll().size();

        // Create the LineaOrden with an existing ID
        lineaOrden.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLineaOrdenMockMvc.perform(post("/api/linea-ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineaOrden)))
            .andExpect(status().isBadRequest());

        // Validate the LineaOrden in the database
        List<LineaOrden> lineaOrdenList = lineaOrdenRepository.findAll();
        assertThat(lineaOrdenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLineaOrdens() throws Exception {
        // Initialize the database
        lineaOrdenRepository.saveAndFlush(lineaOrden);

        // Get all the lineaOrdenList
        restLineaOrdenMockMvc.perform(get("/api/linea-ordens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lineaOrden.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].detalle").value(hasItem(DEFAULT_DETALLE)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())));
    }
    
    @Test
    @Transactional
    public void getLineaOrden() throws Exception {
        // Initialize the database
        lineaOrdenRepository.saveAndFlush(lineaOrden);

        // Get the lineaOrden
        restLineaOrdenMockMvc.perform(get("/api/linea-ordens/{id}", lineaOrden.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lineaOrden.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.detalle").value(DEFAULT_DETALLE))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLineaOrden() throws Exception {
        // Get the lineaOrden
        restLineaOrdenMockMvc.perform(get("/api/linea-ordens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLineaOrden() throws Exception {
        // Initialize the database
        lineaOrdenRepository.saveAndFlush(lineaOrden);

        int databaseSizeBeforeUpdate = lineaOrdenRepository.findAll().size();

        // Update the lineaOrden
        LineaOrden updatedLineaOrden = lineaOrdenRepository.findById(lineaOrden.getId()).get();
        // Disconnect from session so that the updates on updatedLineaOrden are not directly saved in db
        em.detach(updatedLineaOrden);
        updatedLineaOrden
            .codigo(UPDATED_CODIGO)
            .detalle(UPDATED_DETALLE)
            .cantidad(UPDATED_CANTIDAD)
            .precio(UPDATED_PRECIO);

        restLineaOrdenMockMvc.perform(put("/api/linea-ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLineaOrden)))
            .andExpect(status().isOk());

        // Validate the LineaOrden in the database
        List<LineaOrden> lineaOrdenList = lineaOrdenRepository.findAll();
        assertThat(lineaOrdenList).hasSize(databaseSizeBeforeUpdate);
        LineaOrden testLineaOrden = lineaOrdenList.get(lineaOrdenList.size() - 1);
        assertThat(testLineaOrden.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testLineaOrden.getDetalle()).isEqualTo(UPDATED_DETALLE);
        assertThat(testLineaOrden.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testLineaOrden.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingLineaOrden() throws Exception {
        int databaseSizeBeforeUpdate = lineaOrdenRepository.findAll().size();

        // Create the LineaOrden

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLineaOrdenMockMvc.perform(put("/api/linea-ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineaOrden)))
            .andExpect(status().isBadRequest());

        // Validate the LineaOrden in the database
        List<LineaOrden> lineaOrdenList = lineaOrdenRepository.findAll();
        assertThat(lineaOrdenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLineaOrden() throws Exception {
        // Initialize the database
        lineaOrdenRepository.saveAndFlush(lineaOrden);

        int databaseSizeBeforeDelete = lineaOrdenRepository.findAll().size();

        // Delete the lineaOrden
        restLineaOrdenMockMvc.perform(delete("/api/linea-ordens/{id}", lineaOrden.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LineaOrden> lineaOrdenList = lineaOrdenRepository.findAll();
        assertThat(lineaOrdenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
