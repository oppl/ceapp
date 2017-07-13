package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.CurriculumSemester;
import at.meroff.itproject.repository.CurriculumSemesterRepository;
import at.meroff.itproject.service.CurriculumSemesterService;
import at.meroff.itproject.repository.search.CurriculumSemesterSearchRepository;
import at.meroff.itproject.service.dto.CurriculumSemesterDTO;
import at.meroff.itproject.service.mapper.CurriculumSemesterMapper;
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
 * Test class for the CurriculumSemesterResource REST controller.
 *
 * @see CurriculumSemesterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class CurriculumSemesterResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Semester DEFAULT_SEMESTER = Semester.WS;
    private static final Semester UPDATED_SEMESTER = Semester.SS;

    @Autowired
    private CurriculumSemesterRepository curriculumSemesterRepository;

    @Autowired
    private CurriculumSemesterMapper curriculumSemesterMapper;

    @Autowired
    private CurriculumSemesterService curriculumSemesterService;

    @Autowired
    private CurriculumSemesterSearchRepository curriculumSemesterSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCurriculumSemesterMockMvc;

    private CurriculumSemester curriculumSemester;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CurriculumSemesterResource curriculumSemesterResource = new CurriculumSemesterResource(curriculumSemesterService);
        this.restCurriculumSemesterMockMvc = MockMvcBuilders.standaloneSetup(curriculumSemesterResource)
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
    public static CurriculumSemester createEntity(EntityManager em) {
        CurriculumSemester curriculumSemester = new CurriculumSemester()
            .year(DEFAULT_YEAR)
            .semester(DEFAULT_SEMESTER);
        return curriculumSemester;
    }

    @Before
    public void initTest() {
        curriculumSemesterSearchRepository.deleteAll();
        curriculumSemester = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurriculumSemester() throws Exception {
        int databaseSizeBeforeCreate = curriculumSemesterRepository.findAll().size();

        // Create the CurriculumSemester
        CurriculumSemesterDTO curriculumSemesterDTO = curriculumSemesterMapper.toDto(curriculumSemester);
        restCurriculumSemesterMockMvc.perform(post("/api/curriculum-semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumSemesterDTO)))
            .andExpect(status().isCreated());

        // Validate the CurriculumSemester in the database
        List<CurriculumSemester> curriculumSemesterList = curriculumSemesterRepository.findAll();
        assertThat(curriculumSemesterList).hasSize(databaseSizeBeforeCreate + 1);
        CurriculumSemester testCurriculumSemester = curriculumSemesterList.get(curriculumSemesterList.size() - 1);
        assertThat(testCurriculumSemester.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testCurriculumSemester.getSemester()).isEqualTo(DEFAULT_SEMESTER);

        // Validate the CurriculumSemester in Elasticsearch
        CurriculumSemester curriculumSemesterEs = curriculumSemesterSearchRepository.findOne(testCurriculumSemester.getId());
        assertThat(curriculumSemesterEs).isEqualToComparingFieldByField(testCurriculumSemester);
    }

    @Test
    @Transactional
    public void createCurriculumSemesterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = curriculumSemesterRepository.findAll().size();

        // Create the CurriculumSemester with an existing ID
        curriculumSemester.setId(1L);
        CurriculumSemesterDTO curriculumSemesterDTO = curriculumSemesterMapper.toDto(curriculumSemester);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurriculumSemesterMockMvc.perform(post("/api/curriculum-semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumSemesterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CurriculumSemester> curriculumSemesterList = curriculumSemesterRepository.findAll();
        assertThat(curriculumSemesterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCurriculumSemesters() throws Exception {
        // Initialize the database
        curriculumSemesterRepository.saveAndFlush(curriculumSemester);

        // Get all the curriculumSemesterList
        restCurriculumSemesterMockMvc.perform(get("/api/curriculum-semesters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curriculumSemester.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())));
    }

    @Test
    @Transactional
    public void getCurriculumSemester() throws Exception {
        // Initialize the database
        curriculumSemesterRepository.saveAndFlush(curriculumSemester);

        // Get the curriculumSemester
        restCurriculumSemesterMockMvc.perform(get("/api/curriculum-semesters/{id}", curriculumSemester.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(curriculumSemester.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCurriculumSemester() throws Exception {
        // Get the curriculumSemester
        restCurriculumSemesterMockMvc.perform(get("/api/curriculum-semesters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurriculumSemester() throws Exception {
        // Initialize the database
        curriculumSemesterRepository.saveAndFlush(curriculumSemester);
        curriculumSemesterSearchRepository.save(curriculumSemester);
        int databaseSizeBeforeUpdate = curriculumSemesterRepository.findAll().size();

        // Update the curriculumSemester
        CurriculumSemester updatedCurriculumSemester = curriculumSemesterRepository.findOne(curriculumSemester.getId());
        updatedCurriculumSemester
            .year(UPDATED_YEAR)
            .semester(UPDATED_SEMESTER);
        CurriculumSemesterDTO curriculumSemesterDTO = curriculumSemesterMapper.toDto(updatedCurriculumSemester);

        restCurriculumSemesterMockMvc.perform(put("/api/curriculum-semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumSemesterDTO)))
            .andExpect(status().isOk());

        // Validate the CurriculumSemester in the database
        List<CurriculumSemester> curriculumSemesterList = curriculumSemesterRepository.findAll();
        assertThat(curriculumSemesterList).hasSize(databaseSizeBeforeUpdate);
        CurriculumSemester testCurriculumSemester = curriculumSemesterList.get(curriculumSemesterList.size() - 1);
        assertThat(testCurriculumSemester.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testCurriculumSemester.getSemester()).isEqualTo(UPDATED_SEMESTER);

        // Validate the CurriculumSemester in Elasticsearch
        CurriculumSemester curriculumSemesterEs = curriculumSemesterSearchRepository.findOne(testCurriculumSemester.getId());
        assertThat(curriculumSemesterEs).isEqualToComparingFieldByField(testCurriculumSemester);
    }

    @Test
    @Transactional
    public void updateNonExistingCurriculumSemester() throws Exception {
        int databaseSizeBeforeUpdate = curriculumSemesterRepository.findAll().size();

        // Create the CurriculumSemester
        CurriculumSemesterDTO curriculumSemesterDTO = curriculumSemesterMapper.toDto(curriculumSemester);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCurriculumSemesterMockMvc.perform(put("/api/curriculum-semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumSemesterDTO)))
            .andExpect(status().isCreated());

        // Validate the CurriculumSemester in the database
        List<CurriculumSemester> curriculumSemesterList = curriculumSemesterRepository.findAll();
        assertThat(curriculumSemesterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCurriculumSemester() throws Exception {
        // Initialize the database
        curriculumSemesterRepository.saveAndFlush(curriculumSemester);
        curriculumSemesterSearchRepository.save(curriculumSemester);
        int databaseSizeBeforeDelete = curriculumSemesterRepository.findAll().size();

        // Get the curriculumSemester
        restCurriculumSemesterMockMvc.perform(delete("/api/curriculum-semesters/{id}", curriculumSemester.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean curriculumSemesterExistsInEs = curriculumSemesterSearchRepository.exists(curriculumSemester.getId());
        assertThat(curriculumSemesterExistsInEs).isFalse();

        // Validate the database is empty
        List<CurriculumSemester> curriculumSemesterList = curriculumSemesterRepository.findAll();
        assertThat(curriculumSemesterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCurriculumSemester() throws Exception {
        // Initialize the database
        curriculumSemesterRepository.saveAndFlush(curriculumSemester);
        curriculumSemesterSearchRepository.save(curriculumSemester);

        // Search the curriculumSemester
        restCurriculumSemesterMockMvc.perform(get("/api/_search/curriculum-semesters?query=id:" + curriculumSemester.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curriculumSemester.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurriculumSemester.class);
        CurriculumSemester curriculumSemester1 = new CurriculumSemester();
        curriculumSemester1.setId(1L);
        CurriculumSemester curriculumSemester2 = new CurriculumSemester();
        curriculumSemester2.setId(curriculumSemester1.getId());
        assertThat(curriculumSemester1).isEqualTo(curriculumSemester2);
        curriculumSemester2.setId(2L);
        assertThat(curriculumSemester1).isNotEqualTo(curriculumSemester2);
        curriculumSemester1.setId(null);
        assertThat(curriculumSemester1).isNotEqualTo(curriculumSemester2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurriculumSemesterDTO.class);
        CurriculumSemesterDTO curriculumSemesterDTO1 = new CurriculumSemesterDTO();
        curriculumSemesterDTO1.setId(1L);
        CurriculumSemesterDTO curriculumSemesterDTO2 = new CurriculumSemesterDTO();
        assertThat(curriculumSemesterDTO1).isNotEqualTo(curriculumSemesterDTO2);
        curriculumSemesterDTO2.setId(curriculumSemesterDTO1.getId());
        assertThat(curriculumSemesterDTO1).isEqualTo(curriculumSemesterDTO2);
        curriculumSemesterDTO2.setId(2L);
        assertThat(curriculumSemesterDTO1).isNotEqualTo(curriculumSemesterDTO2);
        curriculumSemesterDTO1.setId(null);
        assertThat(curriculumSemesterDTO1).isNotEqualTo(curriculumSemesterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(curriculumSemesterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(curriculumSemesterMapper.fromId(null)).isNull();
    }
}
