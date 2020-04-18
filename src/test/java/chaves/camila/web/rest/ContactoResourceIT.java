package chaves.camila.web.rest;

import chaves.camila.EdgarBotApp;
import chaves.camila.domain.Contacto;
import chaves.camila.repository.ContactoRepository;
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
 * Integration tests for the {@link ContactoResource} REST controller.
 */
@SpringBootTest(classes = EdgarBotApp.class)
public class ContactoResourceIT {

    private static final String DEFAULT_NOMBRE_PERSONA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PERSONA = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR_WA = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_WA = "BBBBBBBBBB";

    @Autowired
    private ContactoRepository contactoRepository;

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

    private MockMvc restContactoMockMvc;

    private Contacto contacto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactoResource contactoResource = new ContactoResource(contactoRepository);
        this.restContactoMockMvc = MockMvcBuilders.standaloneSetup(contactoResource)
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
    public static Contacto createEntity(EntityManager em) {
        Contacto contacto = new Contacto()
            .nombrePersona(DEFAULT_NOMBRE_PERSONA)
            .authorWA(DEFAULT_AUTHOR_WA);
        return contacto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contacto createUpdatedEntity(EntityManager em) {
        Contacto contacto = new Contacto()
            .nombrePersona(UPDATED_NOMBRE_PERSONA)
            .authorWA(UPDATED_AUTHOR_WA);
        return contacto;
    }

    @BeforeEach
    public void initTest() {
        contacto = createEntity(em);
    }

    @Test
    @Transactional
    public void createContacto() throws Exception {
        int databaseSizeBeforeCreate = contactoRepository.findAll().size();

        // Create the Contacto
        restContactoMockMvc.perform(post("/api/contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contacto)))
            .andExpect(status().isCreated());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeCreate + 1);
        Contacto testContacto = contactoList.get(contactoList.size() - 1);
        assertThat(testContacto.getNombrePersona()).isEqualTo(DEFAULT_NOMBRE_PERSONA);
        assertThat(testContacto.getAuthorWA()).isEqualTo(DEFAULT_AUTHOR_WA);
    }

    @Test
    @Transactional
    public void createContactoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactoRepository.findAll().size();

        // Create the Contacto with an existing ID
        contacto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactoMockMvc.perform(post("/api/contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contacto)))
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContactos() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        // Get all the contactoList
        restContactoMockMvc.perform(get("/api/contactos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contacto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrePersona").value(hasItem(DEFAULT_NOMBRE_PERSONA)))
            .andExpect(jsonPath("$.[*].authorWA").value(hasItem(DEFAULT_AUTHOR_WA)));
    }
    
    @Test
    @Transactional
    public void getContacto() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        // Get the contacto
        restContactoMockMvc.perform(get("/api/contactos/{id}", contacto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contacto.getId().intValue()))
            .andExpect(jsonPath("$.nombrePersona").value(DEFAULT_NOMBRE_PERSONA))
            .andExpect(jsonPath("$.authorWA").value(DEFAULT_AUTHOR_WA));
    }

    @Test
    @Transactional
    public void getNonExistingContacto() throws Exception {
        // Get the contacto
        restContactoMockMvc.perform(get("/api/contactos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContacto() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();

        // Update the contacto
        Contacto updatedContacto = contactoRepository.findById(contacto.getId()).get();
        // Disconnect from session so that the updates on updatedContacto are not directly saved in db
        em.detach(updatedContacto);
        updatedContacto
            .nombrePersona(UPDATED_NOMBRE_PERSONA)
            .authorWA(UPDATED_AUTHOR_WA);

        restContactoMockMvc.perform(put("/api/contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContacto)))
            .andExpect(status().isOk());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
        Contacto testContacto = contactoList.get(contactoList.size() - 1);
        assertThat(testContacto.getNombrePersona()).isEqualTo(UPDATED_NOMBRE_PERSONA);
        assertThat(testContacto.getAuthorWA()).isEqualTo(UPDATED_AUTHOR_WA);
    }

    @Test
    @Transactional
    public void updateNonExistingContacto() throws Exception {
        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();

        // Create the Contacto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoMockMvc.perform(put("/api/contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contacto)))
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContacto() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        int databaseSizeBeforeDelete = contactoRepository.findAll().size();

        // Delete the contacto
        restContactoMockMvc.perform(delete("/api/contactos/{id}", contacto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
