package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.IdealPlan;
import at.meroff.itproject.repository.IdealPlanRepository;
import at.meroff.itproject.service.IdealPlanService;
import at.meroff.itproject.repository.search.IdealPlanSearchRepository;
import at.meroff.itproject.service.dto.IdealPlanDTO;
import at.meroff.itproject.service.mapper.IdealPlanMapper;
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

import at.meroff.itproject.domain.enumeration.Semester;
/**
 * Test class for the IdealPlanResource REST controller.
 *
 * @see IdealPlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class IdealPlanResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Semester DEFAULT_SEMESTER = Semester.WS;
    private static final Semester UPDATED_SEMESTER = Semester.SS;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private IdealPlanRepository idealPlanRepository;

    @Autowired
    private IdealPlanMapper idealPlanMapper;

    @Autowired
    private IdealPlanService idealPlanService;

    @Autowired
    private IdealPlanSearchRepository idealPlanSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIdealPlanMockMvc;

    private IdealPlan idealPlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IdealPlanResource idealPlanResource = new IdealPlanResource(idealPlanService);
        this.restIdealPlanMockMvc = MockMvcBuilders.standaloneSetup(idealPlanResource)
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
    public static IdealPlan createEntity(EntityManager em) {
        IdealPlan idealPlan = new IdealPlan()
            .year(DEFAULT_YEAR)
            .semester(DEFAULT_SEMESTER)
            .active(DEFAULT_ACTIVE);
        return idealPlan;
    }

    @Before
    public void initTest() {
        idealPlanSearchRepository.deleteAll();
        idealPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdealPlan() throws Exception {
        int databaseSizeBeforeCreate = idealPlanRepository.findAll().size();

        // Create the IdealPlan
        IdealPlanDTO idealPlanDTO = idealPlanMapper.toDto(idealPlan);
        restIdealPlanMockMvc.perform(post("/api/ideal-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idealPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the IdealPlan in the database
        List<IdealPlan> idealPlanList = idealPlanRepository.findAll();
        assertThat(idealPlanList).hasSize(databaseSizeBeforeCreate + 1);
        IdealPlan testIdealPlan = idealPlanList.get(idealPlanList.size() - 1);
        assertThat(testIdealPlan.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testIdealPlan.getSemester()).isEqualTo(DEFAULT_SEMESTER);
        assertThat(testIdealPlan.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the IdealPlan in Elasticsearch
        IdealPlan idealPlanEs = idealPlanSearchRepository.findOne(testIdealPlan.getId());
        assertThat(idealPlanEs).isEqualToComparingFieldByField(testIdealPlan);
    }

    @Test
    @Transactional
    public void createIdealPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = idealPlanRepository.findAll().size();

        // Create the IdealPlan with an existing ID
        idealPlan.setId(1L);
        IdealPlanDTO idealPlanDTO = idealPlanMapper.toDto(idealPlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdealPlanMockMvc.perform(post("/api/ideal-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idealPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IdealPlan> idealPlanList = idealPlanRepository.findAll();
        assertThat(idealPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = idealPlanRepository.findAll().size();
        // set the field null
        idealPlan.setYear(null);

        // Create the IdealPlan, which fails.
        IdealPlanDTO idealPlanDTO = idealPlanMapper.toDto(idealPlan);

        restIdealPlanMockMvc.perform(post("/api/ideal-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idealPlanDTO)))
            .andExpect(status().isBadRequest());

        List<IdealPlan> idealPlanList = idealPlanRepository.findAll();
        assertThat(idealPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSemesterIsRequired() throws Exception {
        int databaseSizeBeforeTest = idealPlanRepository.findAll().size();
        // set the field null
        idealPlan.setSemester(null);

        // Create the IdealPlan, which fails.
        IdealPlanDTO idealPlanDTO = idealPlanMapper.toDto(idealPlan);

        restIdealPlanMockMvc.perform(post("/api/ideal-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idealPlanDTO)))
            .andExpect(status().isBadRequest());

        List<IdealPlan> idealPlanList = idealPlanRepository.findAll();
        assertThat(idealPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIdealPlans() throws Exception {
        // Initialize the database
        idealPlanRepository.saveAndFlush(idealPlan);

        // Get all the idealPlanList
        restIdealPlanMockMvc.perform(get("/api/ideal-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idealPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getIdealPlan() throws Exception {
        // Initialize the database
        idealPlanRepository.saveAndFlush(idealPlan);

        // Get the idealPlan
        restIdealPlanMockMvc.perform(get("/api/ideal-plans/{id}", idealPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(idealPlan.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIdealPlan() throws Exception {
        // Get the idealPlan
        restIdealPlanMockMvc.perform(get("/api/ideal-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdealPlan() throws Exception {
        // Initialize the database
        idealPlanRepository.saveAndFlush(idealPlan);
        idealPlanSearchRepository.save(idealPlan);
        int databaseSizeBeforeUpdate = idealPlanRepository.findAll().size();

        // Update the idealPlan
        IdealPlan updatedIdealPlan = idealPlanRepository.findOne(idealPlan.getId());
        updatedIdealPlan
            .year(UPDATED_YEAR)
            .semester(UPDATED_SEMESTER)
            .active(UPDATED_ACTIVE);
        IdealPlanDTO idealPlanDTO = idealPlanMapper.toDto(updatedIdealPlan);

        restIdealPlanMockMvc.perform(put("/api/ideal-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idealPlanDTO)))
            .andExpect(status().isOk());

        // Validate the IdealPlan in the database
        List<IdealPlan> idealPlanList = idealPlanRepository.findAll();
        assertThat(idealPlanList).hasSize(databaseSizeBeforeUpdate);
        IdealPlan testIdealPlan = idealPlanList.get(idealPlanList.size() - 1);
        assertThat(testIdealPlan.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testIdealPlan.getSemester()).isEqualTo(UPDATED_SEMESTER);
        assertThat(testIdealPlan.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the IdealPlan in Elasticsearch
        IdealPlan idealPlanEs = idealPlanSearchRepository.findOne(testIdealPlan.getId());
        assertThat(idealPlanEs).isEqualToComparingFieldByField(testIdealPlan);
    }

    @Test
    @Transactional
    public void updateNonExistingIdealPlan() throws Exception {
        int databaseSizeBeforeUpdate = idealPlanRepository.findAll().size();

        // Create the IdealPlan
        IdealPlanDTO idealPlanDTO = idealPlanMapper.toDto(idealPlan);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIdealPlanMockMvc.perform(put("/api/ideal-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idealPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the IdealPlan in the database
        List<IdealPlan> idealPlanList = idealPlanRepository.findAll();
        assertThat(idealPlanList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIdealPlan() throws Exception {
        // Initialize the database
        idealPlanRepository.saveAndFlush(idealPlan);
        idealPlanSearchRepository.save(idealPlan);
        int databaseSizeBeforeDelete = idealPlanRepository.findAll().size();

        // Get the idealPlan
        restIdealPlanMockMvc.perform(delete("/api/ideal-plans/{id}", idealPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean idealPlanExistsInEs = idealPlanSearchRepository.exists(idealPlan.getId());
        assertThat(idealPlanExistsInEs).isFalse();

        // Validate the database is empty
        List<IdealPlan> idealPlanList = idealPlanRepository.findAll();
        assertThat(idealPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIdealPlan() throws Exception {
        // Initialize the database
        idealPlanRepository.saveAndFlush(idealPlan);
        idealPlanSearchRepository.save(idealPlan);

        // Search the idealPlan
        restIdealPlanMockMvc.perform(get("/api/_search/ideal-plans?query=id:" + idealPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idealPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdealPlan.class);
        IdealPlan idealPlan1 = new IdealPlan();
        idealPlan1.setId(1L);
        IdealPlan idealPlan2 = new IdealPlan();
        idealPlan2.setId(idealPlan1.getId());
        assertThat(idealPlan1).isEqualTo(idealPlan2);
        idealPlan2.setId(2L);
        assertThat(idealPlan1).isNotEqualTo(idealPlan2);
        idealPlan1.setId(null);
        assertThat(idealPlan1).isNotEqualTo(idealPlan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdealPlanDTO.class);
        IdealPlanDTO idealPlanDTO1 = new IdealPlanDTO();
        idealPlanDTO1.setId(1L);
        IdealPlanDTO idealPlanDTO2 = new IdealPlanDTO();
        assertThat(idealPlanDTO1).isNotEqualTo(idealPlanDTO2);
        idealPlanDTO2.setId(idealPlanDTO1.getId());
        assertThat(idealPlanDTO1).isEqualTo(idealPlanDTO2);
        idealPlanDTO2.setId(2L);
        assertThat(idealPlanDTO1).isNotEqualTo(idealPlanDTO2);
        idealPlanDTO1.setId(null);
        assertThat(idealPlanDTO1).isNotEqualTo(idealPlanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(idealPlanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(idealPlanMapper.fromId(null)).isNull();
    }
}
