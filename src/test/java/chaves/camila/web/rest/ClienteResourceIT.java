package chaves.camila.web.rest;

import chaves.camila.EdgarBotApp;
import chaves.camila.domain.Cliente;
import chaves.camila.repository.ClienteRepository;
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
 * Integration tests for the {@link ClienteResource} REST controller.
 */
@SpringBootTest(classes = EdgarBotApp.class)
public class ClienteResourceIT {

    private static final String DEFAULT_NOMBRE_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_EMPRESA = "BBBBBBBBBB";

    @Autowired
    private ClienteRepository clienteRepository;

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

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteResource clienteResource = new ClienteResource(clienteRepository);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
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
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .nombreEmpresa(DEFAULT_NOMBRE_EMPRESA);
        return cliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createUpdatedEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .nombreEmpresa(UPDATED_NOMBRE_EMPRESA);
        return cliente;
    }

    @BeforeEach
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNombreEmpresa()).isEqualTo(DEFAULT_NOMBRE_EMPRESA);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreEmpresa").value(hasItem(DEFAULT_NOMBRE_EMPRESA)));
    }
    
    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.nombreEmpresa").value(DEFAULT_NOMBRE_EMPRESA));
    }

    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .nombreEmpresa(UPDATED_NOMBRE_EMPRESA);

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCliente)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNombreEmpresa()).isEqualTo(UPDATED_NOMBRE_EMPRESA);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Create the Cliente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
