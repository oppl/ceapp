package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.Institute;
import at.meroff.itproject.repository.InstituteRepository;
import at.meroff.itproject.service.InstituteService;
import at.meroff.itproject.service.dto.InstituteDTO;
import at.meroff.itproject.service.mapper.InstituteMapper;
import at.meroff.itproject.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InstituteResource REST controller.
 *
 * @see InstituteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class InstituteResourceIntTest {

    private static final Integer DEFAULT_INSTITUTE_ID = 1;
    private static final Integer UPDATED_INSTITUTE_ID = 2;

    private static final String DEFAULT_INSTITUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTE_NAME = "BBBBBBBBBB";

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private InstituteMapper instituteMapper;

    @Autowired
    private InstituteService instituteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInstituteMockMvc;

    private Institute institute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstituteResource instituteResource = new InstituteResource(instituteService);
        this.restInstituteMockMvc = MockMvcBuilders.standaloneSetup(instituteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Institute createEntity(EntityManager em) {
        Institute institute = new Institute()
            .instituteId(DEFAULT_INSTITUTE_ID)
            .instituteName(DEFAULT_INSTITUTE_NAME);
        return institute;
    }

    @Before
    public void initTest() {
        institute = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstitute() throws Exception {
        int databaseSizeBeforeCreate = instituteRepository.findAll().size();

        // Create the Institute
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);
        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isCreated());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeCreate + 1);
        Institute testInstitute = instituteList.get(instituteList.size() - 1);
        assertThat(testInstitute.getInstituteId()).isEqualTo(DEFAULT_INSTITUTE_ID);
        assertThat(testInstitute.getInstituteName()).isEqualTo(DEFAULT_INSTITUTE_NAME);
    }

    @Test
    @Transactional
    public void createInstituteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instituteRepository.findAll().size();

        // Create the Institute with an existing ID
        institute.setId(1L);
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkInstituteIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setInstituteId(null);

        // Create the Institute, which fails.
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);

        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstituteNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setInstituteName(null);

        // Create the Institute, which fails.
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);

        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstitutes() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList
        restInstituteMockMvc.perform(get("/api/institutes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(institute.getId().intValue())))
            .andExpect(jsonPath("$.[*].instituteId").value(hasItem(DEFAULT_INSTITUTE_ID)))
            .andExpect(jsonPath("$.[*].instituteName").value(hasItem(DEFAULT_INSTITUTE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getInstitute() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get the institute
        restInstituteMockMvc.perform(get("/api/institutes/{id}", institute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(institute.getId().intValue()))
            .andExpect(jsonPath("$.instituteId").value(DEFAULT_INSTITUTE_ID))
            .andExpect(jsonPath("$.instituteName").value(DEFAULT_INSTITUTE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstitute() throws Exception {
        // Get the institute
        restInstituteMockMvc.perform(get("/api/institutes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstitute() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);
        int databaseSizeBeforeUpdate = instituteRepository.findAll().size();

        // Update the institute
        Institute updatedInstitute = instituteRepository.findOne(institute.getId());
        updatedInstitute
            .instituteId(UPDATED_INSTITUTE_ID)
            .instituteName(UPDATED_INSTITUTE_NAME);
        InstituteDTO instituteDTO = instituteMapper.toDto(updatedInstitute);

        restInstituteMockMvc.perform(put("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isOk());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeUpdate);
        Institute testInstitute = instituteList.get(instituteList.size() - 1);
        assertThat(testInstitute.getInstituteId()).isEqualTo(UPDATED_INSTITUTE_ID);
        assertThat(testInstitute.getInstituteName()).isEqualTo(UPDATED_INSTITUTE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingInstitute() throws Exception {
        int databaseSizeBeforeUpdate = instituteRepository.findAll().size();

        // Create the Institute
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInstituteMockMvc.perform(put("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isCreated());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInstitute() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);
        int databaseSizeBeforeDelete = instituteRepository.findAll().size();

        // Get the institute
        restInstituteMockMvc.perform(delete("/api/institutes/{id}", institute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Institute.class);
        Institute institute1 = new Institute();
        institute1.setId(1L);
        Institute institute2 = new Institute();
        institute2.setId(institute1.getId());
        assertThat(institute1).isEqualTo(institute2);
        institute2.setId(2L);
        assertThat(institute1).isNotEqualTo(institute2);
        institute1.setId(null);
        assertThat(institute1).isNotEqualTo(institute2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstituteDTO.class);
        InstituteDTO instituteDTO1 = new InstituteDTO();
        instituteDTO1.setId(1L);
        InstituteDTO instituteDTO2 = new InstituteDTO();
        assertThat(instituteDTO1).isNotEqualTo(instituteDTO2);
        instituteDTO2.setId(instituteDTO1.getId());
        assertThat(instituteDTO1).isEqualTo(instituteDTO2);
        instituteDTO2.setId(2L);
        assertThat(instituteDTO1).isNotEqualTo(instituteDTO2);
        instituteDTO1.setId(null);
        assertThat(instituteDTO1).isNotEqualTo(instituteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(instituteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(instituteMapper.fromId(null)).isNull();
    }
}
