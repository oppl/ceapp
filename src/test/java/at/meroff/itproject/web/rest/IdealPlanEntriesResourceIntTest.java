package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.IdealPlanEntries;
import at.meroff.itproject.repository.IdealPlanEntriesRepository;
import at.meroff.itproject.service.IdealPlanEntriesService;
import at.meroff.itproject.repository.search.IdealPlanEntriesSearchRepository;
import at.meroff.itproject.service.dto.IdealPlanEntriesDTO;
import at.meroff.itproject.service.mapper.IdealPlanEntriesMapper;
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
 * Test class for the IdealPlanEntriesResource REST controller.
 *
 * @see IdealPlanEntriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class IdealPlanEntriesResourceIntTest {

    private static final Integer DEFAULT_WINTER_SEMESTER_DEFAULT = 1;
    private static final Integer UPDATED_WINTER_SEMESTER_DEFAULT = 2;

    private static final Integer DEFAULT_SUMMER_SEMESTER_DEFAULT = 1;
    private static final Integer UPDATED_SUMMER_SEMESTER_DEFAULT = 2;

    private static final Boolean DEFAULT_OPTIONAL_SUBJECT = false;
    private static final Boolean UPDATED_OPTIONAL_SUBJECT = true;

    private static final Boolean DEFAULT_EXCLUSIVE = false;
    private static final Boolean UPDATED_EXCLUSIVE = true;

    @Autowired
    private IdealPlanEntriesRepository idealPlanEntriesRepository;

    @Autowired
    private IdealPlanEntriesMapper idealPlanEntriesMapper;

    @Autowired
    private IdealPlanEntriesService idealPlanEntriesService;

    @Autowired
    private IdealPlanEntriesSearchRepository idealPlanEntriesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIdealPlanEntriesMockMvc;

    private IdealPlanEntries idealPlanEntries;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IdealPlanEntriesResource idealPlanEntriesResource = new IdealPlanEntriesResource(idealPlanEntriesService);
        this.restIdealPlanEntriesMockMvc = MockMvcBuilders.standaloneSetup(idealPlanEntriesResource)
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
    public static IdealPlanEntries createEntity(EntityManager em) {
        IdealPlanEntries idealPlanEntries = new IdealPlanEntries()
            .winterSemesterDefault(DEFAULT_WINTER_SEMESTER_DEFAULT)
            .summerSemesterDefault(DEFAULT_SUMMER_SEMESTER_DEFAULT)
            .optionalSubject(DEFAULT_OPTIONAL_SUBJECT)
            .exclusive(DEFAULT_EXCLUSIVE);
        return idealPlanEntries;
    }

    @Before
    public void initTest() {
        idealPlanEntriesSearchRepository.deleteAll();
        idealPlanEntries = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdealPlanEntries() throws Exception {
        int databaseSizeBeforeCreate = idealPlanEntriesRepository.findAll().size();

        // Create the IdealPlanEntries
        IdealPlanEntriesDTO idealPlanEntriesDTO = idealPlanEntriesMapper.toDto(idealPlanEntries);
        restIdealPlanEntriesMockMvc.perform(post("/api/ideal-plan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idealPlanEntriesDTO)))
            .andExpect(status().isCreated());

        // Validate the IdealPlanEntries in the database
        List<IdealPlanEntries> idealPlanEntriesList = idealPlanEntriesRepository.findAll();
        assertThat(idealPlanEntriesList).hasSize(databaseSizeBeforeCreate + 1);
        IdealPlanEntries testIdealPlanEntries = idealPlanEntriesList.get(idealPlanEntriesList.size() - 1);
        assertThat(testIdealPlanEntries.getWinterSemesterDefault()).isEqualTo(DEFAULT_WINTER_SEMESTER_DEFAULT);
        assertThat(testIdealPlanEntries.getSummerSemesterDefault()).isEqualTo(DEFAULT_SUMMER_SEMESTER_DEFAULT);
        assertThat(testIdealPlanEntries.isOptionalSubject()).isEqualTo(DEFAULT_OPTIONAL_SUBJECT);
        assertThat(testIdealPlanEntries.isExclusive()).isEqualTo(DEFAULT_EXCLUSIVE);

        // Validate the IdealPlanEntries in Elasticsearch
        IdealPlanEntries idealPlanEntriesEs = idealPlanEntriesSearchRepository.findOne(testIdealPlanEntries.getId());
        assertThat(idealPlanEntriesEs).isEqualToComparingFieldByField(testIdealPlanEntries);
    }

    @Test
    @Transactional
    public void createIdealPlanEntriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = idealPlanEntriesRepository.findAll().size();

        // Create the IdealPlanEntries with an existing ID
        idealPlanEntries.setId(1L);
        IdealPlanEntriesDTO idealPlanEntriesDTO = idealPlanEntriesMapper.toDto(idealPlanEntries);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdealPlanEntriesMockMvc.perform(post("/api/ideal-plan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idealPlanEntriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IdealPlanEntries> idealPlanEntriesList = idealPlanEntriesRepository.findAll();
        assertThat(idealPlanEntriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWinterSemesterDefaultIsRequired() throws Exception {
        int databaseSizeBeforeTest = idealPlanEntriesRepository.findAll().size();
        // set the field null
        idealPlanEntries.setWinterSemesterDefault(null);

        // Create the IdealPlanEntries, which fails.
        IdealPlanEntriesDTO idealPlanEntriesDTO = idealPlanEntriesMapper.toDto(idealPlanEntries);

        restIdealPlanEntriesMockMvc.perform(post("/api/ideal-plan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idealPlanEntriesDTO)))
            .andExpect(status().isBadRequest());

        List<IdealPlanEntries> idealPlanEntriesList = idealPlanEntriesRepository.findAll();
        assertThat(idealPlanEntriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSummerSemesterDefaultIsRequired() throws Exception {
        int databaseSizeBeforeTest = idealPlanEntriesRepository.findAll().size();
        // set the field null
        idealPlanEntries.setSummerSemesterDefault(null);

        // Create the IdealPlanEntries, which fails.
        IdealPlanEntriesDTO idealPlanEntriesDTO = idealPlanEntriesMapper.toDto(idealPlanEntries);

        restIdealPlanEntriesMockMvc.perform(post("/api/ideal-plan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idealPlanEntriesDTO)))
            .andExpect(status().isBadRequest());

        List<IdealPlanEntries> idealPlanEntriesList = idealPlanEntriesRepository.findAll();
        assertThat(idealPlanEntriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIdealPlanEntries() throws Exception {
        // Initialize the database
        idealPlanEntriesRepository.saveAndFlush(idealPlanEntries);

        // Get all the idealPlanEntriesList
        restIdealPlanEntriesMockMvc.perform(get("/api/ideal-plan-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idealPlanEntries.getId().intValue())))
            .andExpect(jsonPath("$.[*].winterSemesterDefault").value(hasItem(DEFAULT_WINTER_SEMESTER_DEFAULT)))
            .andExpect(jsonPath("$.[*].summerSemesterDefault").value(hasItem(DEFAULT_SUMMER_SEMESTER_DEFAULT)))
            .andExpect(jsonPath("$.[*].optionalSubject").value(hasItem(DEFAULT_OPTIONAL_SUBJECT.booleanValue())))
            .andExpect(jsonPath("$.[*].exclusive").value(hasItem(DEFAULT_EXCLUSIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getIdealPlanEntries() throws Exception {
        // Initialize the database
        idealPlanEntriesRepository.saveAndFlush(idealPlanEntries);

        // Get the idealPlanEntries
        restIdealPlanEntriesMockMvc.perform(get("/api/ideal-plan-entries/{id}", idealPlanEntries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(idealPlanEntries.getId().intValue()))
            .andExpect(jsonPath("$.winterSemesterDefault").value(DEFAULT_WINTER_SEMESTER_DEFAULT))
            .andExpect(jsonPath("$.summerSemesterDefault").value(DEFAULT_SUMMER_SEMESTER_DEFAULT))
            .andExpect(jsonPath("$.optionalSubject").value(DEFAULT_OPTIONAL_SUBJECT.booleanValue()))
            .andExpect(jsonPath("$.exclusive").value(DEFAULT_EXCLUSIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIdealPlanEntries() throws Exception {
        // Get the idealPlanEntries
        restIdealPlanEntriesMockMvc.perform(get("/api/ideal-plan-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdealPlanEntries() throws Exception {
        // Initialize the database
        idealPlanEntriesRepository.saveAndFlush(idealPlanEntries);
        idealPlanEntriesSearchRepository.save(idealPlanEntries);
        int databaseSizeBeforeUpdate = idealPlanEntriesRepository.findAll().size();

        // Update the idealPlanEntries
        IdealPlanEntries updatedIdealPlanEntries = idealPlanEntriesRepository.findOne(idealPlanEntries.getId());
        updatedIdealPlanEntries
            .winterSemesterDefault(UPDATED_WINTER_SEMESTER_DEFAULT)
            .summerSemesterDefault(UPDATED_SUMMER_SEMESTER_DEFAULT)
            .optionalSubject(UPDATED_OPTIONAL_SUBJECT)
            .exclusive(UPDATED_EXCLUSIVE);
        IdealPlanEntriesDTO idealPlanEntriesDTO = idealPlanEntriesMapper.toDto(updatedIdealPlanEntries);

        restIdealPlanEntriesMockMvc.perform(put("/api/ideal-plan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idealPlanEntriesDTO)))
            .andExpect(status().isOk());

        // Validate the IdealPlanEntries in the database
        List<IdealPlanEntries> idealPlanEntriesList = idealPlanEntriesRepository.findAll();
        assertThat(idealPlanEntriesList).hasSize(databaseSizeBeforeUpdate);
        IdealPlanEntries testIdealPlanEntries = idealPlanEntriesList.get(idealPlanEntriesList.size() - 1);
        assertThat(testIdealPlanEntries.getWinterSemesterDefault()).isEqualTo(UPDATED_WINTER_SEMESTER_DEFAULT);
        assertThat(testIdealPlanEntries.getSummerSemesterDefault()).isEqualTo(UPDATED_SUMMER_SEMESTER_DEFAULT);
        assertThat(testIdealPlanEntries.isOptionalSubject()).isEqualTo(UPDATED_OPTIONAL_SUBJECT);
        assertThat(testIdealPlanEntries.isExclusive()).isEqualTo(UPDATED_EXCLUSIVE);

        // Validate the IdealPlanEntries in Elasticsearch
        IdealPlanEntries idealPlanEntriesEs = idealPlanEntriesSearchRepository.findOne(testIdealPlanEntries.getId());
        assertThat(idealPlanEntriesEs).isEqualToComparingFieldByField(testIdealPlanEntries);
    }

    @Test
    @Transactional
    public void updateNonExistingIdealPlanEntries() throws Exception {
        int databaseSizeBeforeUpdate = idealPlanEntriesRepository.findAll().size();

        // Create the IdealPlanEntries
        IdealPlanEntriesDTO idealPlanEntriesDTO = idealPlanEntriesMapper.toDto(idealPlanEntries);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIdealPlanEntriesMockMvc.perform(put("/api/ideal-plan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idealPlanEntriesDTO)))
            .andExpect(status().isCreated());

        // Validate the IdealPlanEntries in the database
        List<IdealPlanEntries> idealPlanEntriesList = idealPlanEntriesRepository.findAll();
        assertThat(idealPlanEntriesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIdealPlanEntries() throws Exception {
        // Initialize the database
        idealPlanEntriesRepository.saveAndFlush(idealPlanEntries);
        idealPlanEntriesSearchRepository.save(idealPlanEntries);
        int databaseSizeBeforeDelete = idealPlanEntriesRepository.findAll().size();

        // Get the idealPlanEntries
        restIdealPlanEntriesMockMvc.perform(delete("/api/ideal-plan-entries/{id}", idealPlanEntries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean idealPlanEntriesExistsInEs = idealPlanEntriesSearchRepository.exists(idealPlanEntries.getId());
        assertThat(idealPlanEntriesExistsInEs).isFalse();

        // Validate the database is empty
        List<IdealPlanEntries> idealPlanEntriesList = idealPlanEntriesRepository.findAll();
        assertThat(idealPlanEntriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIdealPlanEntries() throws Exception {
        // Initialize the database
        idealPlanEntriesRepository.saveAndFlush(idealPlanEntries);
        idealPlanEntriesSearchRepository.save(idealPlanEntries);

        // Search the idealPlanEntries
        restIdealPlanEntriesMockMvc.perform(get("/api/_search/ideal-plan-entries?query=id:" + idealPlanEntries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idealPlanEntries.getId().intValue())))
            .andExpect(jsonPath("$.[*].winterSemesterDefault").value(hasItem(DEFAULT_WINTER_SEMESTER_DEFAULT)))
            .andExpect(jsonPath("$.[*].summerSemesterDefault").value(hasItem(DEFAULT_SUMMER_SEMESTER_DEFAULT)))
            .andExpect(jsonPath("$.[*].optionalSubject").value(hasItem(DEFAULT_OPTIONAL_SUBJECT.booleanValue())))
            .andExpect(jsonPath("$.[*].exclusive").value(hasItem(DEFAULT_EXCLUSIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdealPlanEntries.class);
        IdealPlanEntries idealPlanEntries1 = new IdealPlanEntries();
        idealPlanEntries1.setId(1L);
        IdealPlanEntries idealPlanEntries2 = new IdealPlanEntries();
        idealPlanEntries2.setId(idealPlanEntries1.getId());
        assertThat(idealPlanEntries1).isEqualTo(idealPlanEntries2);
        idealPlanEntries2.setId(2L);
        assertThat(idealPlanEntries1).isNotEqualTo(idealPlanEntries2);
        idealPlanEntries1.setId(null);
        assertThat(idealPlanEntries1).isNotEqualTo(idealPlanEntries2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdealPlanEntriesDTO.class);
        IdealPlanEntriesDTO idealPlanEntriesDTO1 = new IdealPlanEntriesDTO();
        idealPlanEntriesDTO1.setId(1L);
        IdealPlanEntriesDTO idealPlanEntriesDTO2 = new IdealPlanEntriesDTO();
        assertThat(idealPlanEntriesDTO1).isNotEqualTo(idealPlanEntriesDTO2);
        idealPlanEntriesDTO2.setId(idealPlanEntriesDTO1.getId());
        assertThat(idealPlanEntriesDTO1).isEqualTo(idealPlanEntriesDTO2);
        idealPlanEntriesDTO2.setId(2L);
        assertThat(idealPlanEntriesDTO1).isNotEqualTo(idealPlanEntriesDTO2);
        idealPlanEntriesDTO1.setId(null);
        assertThat(idealPlanEntriesDTO1).isNotEqualTo(idealPlanEntriesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(idealPlanEntriesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(idealPlanEntriesMapper.fromId(null)).isNull();
    }
}
