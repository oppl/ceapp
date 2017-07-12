package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.CurriculumSubject;
import at.meroff.itproject.repository.CurriculumSubjectRepository;
import at.meroff.itproject.service.CurriculumSubjectService;
import at.meroff.itproject.repository.search.CurriculumSubjectSearchRepository;
import at.meroff.itproject.service.dto.CurriculumSubjectDTO;
import at.meroff.itproject.service.mapper.CurriculumSubjectMapper;
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
 * Test class for the CurriculumSubjectResource REST controller.
 *
 * @see CurriculumSubjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class CurriculumSubjectResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Semester DEFAULT_SEMESTER = Semester.WS;
    private static final Semester UPDATED_SEMESTER = Semester.SS;

    @Autowired
    private CurriculumSubjectRepository curriculumSubjectRepository;

    @Autowired
    private CurriculumSubjectMapper curriculumSubjectMapper;

    @Autowired
    private CurriculumSubjectService curriculumSubjectService;

    @Autowired
    private CurriculumSubjectSearchRepository curriculumSubjectSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCurriculumSubjectMockMvc;

    private CurriculumSubject curriculumSubject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CurriculumSubjectResource curriculumSubjectResource = new CurriculumSubjectResource(curriculumSubjectService);
        this.restCurriculumSubjectMockMvc = MockMvcBuilders.standaloneSetup(curriculumSubjectResource)
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
    public static CurriculumSubject createEntity(EntityManager em) {
        CurriculumSubject curriculumSubject = new CurriculumSubject()
            .year(DEFAULT_YEAR)
            .semester(DEFAULT_SEMESTER);
        return curriculumSubject;
    }

    @Before
    public void initTest() {
        curriculumSubjectSearchRepository.deleteAll();
        curriculumSubject = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurriculumSubject() throws Exception {
        int databaseSizeBeforeCreate = curriculumSubjectRepository.findAll().size();

        // Create the CurriculumSubject
        CurriculumSubjectDTO curriculumSubjectDTO = curriculumSubjectMapper.toDto(curriculumSubject);
        restCurriculumSubjectMockMvc.perform(post("/api/curriculum-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumSubjectDTO)))
            .andExpect(status().isCreated());

        // Validate the CurriculumSubject in the database
        List<CurriculumSubject> curriculumSubjectList = curriculumSubjectRepository.findAll();
        assertThat(curriculumSubjectList).hasSize(databaseSizeBeforeCreate + 1);
        CurriculumSubject testCurriculumSubject = curriculumSubjectList.get(curriculumSubjectList.size() - 1);
        assertThat(testCurriculumSubject.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testCurriculumSubject.getSemester()).isEqualTo(DEFAULT_SEMESTER);

        // Validate the CurriculumSubject in Elasticsearch
        CurriculumSubject curriculumSubjectEs = curriculumSubjectSearchRepository.findOne(testCurriculumSubject.getId());
        assertThat(curriculumSubjectEs).isEqualToComparingFieldByField(testCurriculumSubject);
    }

    @Test
    @Transactional
    public void createCurriculumSubjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = curriculumSubjectRepository.findAll().size();

        // Create the CurriculumSubject with an existing ID
        curriculumSubject.setId(1L);
        CurriculumSubjectDTO curriculumSubjectDTO = curriculumSubjectMapper.toDto(curriculumSubject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurriculumSubjectMockMvc.perform(post("/api/curriculum-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumSubjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CurriculumSubject> curriculumSubjectList = curriculumSubjectRepository.findAll();
        assertThat(curriculumSubjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = curriculumSubjectRepository.findAll().size();
        // set the field null
        curriculumSubject.setYear(null);

        // Create the CurriculumSubject, which fails.
        CurriculumSubjectDTO curriculumSubjectDTO = curriculumSubjectMapper.toDto(curriculumSubject);

        restCurriculumSubjectMockMvc.perform(post("/api/curriculum-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumSubjectDTO)))
            .andExpect(status().isBadRequest());

        List<CurriculumSubject> curriculumSubjectList = curriculumSubjectRepository.findAll();
        assertThat(curriculumSubjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSemesterIsRequired() throws Exception {
        int databaseSizeBeforeTest = curriculumSubjectRepository.findAll().size();
        // set the field null
        curriculumSubject.setSemester(null);

        // Create the CurriculumSubject, which fails.
        CurriculumSubjectDTO curriculumSubjectDTO = curriculumSubjectMapper.toDto(curriculumSubject);

        restCurriculumSubjectMockMvc.perform(post("/api/curriculum-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumSubjectDTO)))
            .andExpect(status().isBadRequest());

        List<CurriculumSubject> curriculumSubjectList = curriculumSubjectRepository.findAll();
        assertThat(curriculumSubjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurriculumSubjects() throws Exception {
        // Initialize the database
        curriculumSubjectRepository.saveAndFlush(curriculumSubject);

        // Get all the curriculumSubjectList
        restCurriculumSubjectMockMvc.perform(get("/api/curriculum-subjects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curriculumSubject.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())));
    }

    @Test
    @Transactional
    public void getCurriculumSubject() throws Exception {
        // Initialize the database
        curriculumSubjectRepository.saveAndFlush(curriculumSubject);

        // Get the curriculumSubject
        restCurriculumSubjectMockMvc.perform(get("/api/curriculum-subjects/{id}", curriculumSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(curriculumSubject.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCurriculumSubject() throws Exception {
        // Get the curriculumSubject
        restCurriculumSubjectMockMvc.perform(get("/api/curriculum-subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurriculumSubject() throws Exception {
        // Initialize the database
        curriculumSubjectRepository.saveAndFlush(curriculumSubject);
        curriculumSubjectSearchRepository.save(curriculumSubject);
        int databaseSizeBeforeUpdate = curriculumSubjectRepository.findAll().size();

        // Update the curriculumSubject
        CurriculumSubject updatedCurriculumSubject = curriculumSubjectRepository.findOne(curriculumSubject.getId());
        updatedCurriculumSubject
            .year(UPDATED_YEAR)
            .semester(UPDATED_SEMESTER);
        CurriculumSubjectDTO curriculumSubjectDTO = curriculumSubjectMapper.toDto(updatedCurriculumSubject);

        restCurriculumSubjectMockMvc.perform(put("/api/curriculum-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumSubjectDTO)))
            .andExpect(status().isOk());

        // Validate the CurriculumSubject in the database
        List<CurriculumSubject> curriculumSubjectList = curriculumSubjectRepository.findAll();
        assertThat(curriculumSubjectList).hasSize(databaseSizeBeforeUpdate);
        CurriculumSubject testCurriculumSubject = curriculumSubjectList.get(curriculumSubjectList.size() - 1);
        assertThat(testCurriculumSubject.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testCurriculumSubject.getSemester()).isEqualTo(UPDATED_SEMESTER);

        // Validate the CurriculumSubject in Elasticsearch
        CurriculumSubject curriculumSubjectEs = curriculumSubjectSearchRepository.findOne(testCurriculumSubject.getId());
        assertThat(curriculumSubjectEs).isEqualToComparingFieldByField(testCurriculumSubject);
    }

    @Test
    @Transactional
    public void updateNonExistingCurriculumSubject() throws Exception {
        int databaseSizeBeforeUpdate = curriculumSubjectRepository.findAll().size();

        // Create the CurriculumSubject
        CurriculumSubjectDTO curriculumSubjectDTO = curriculumSubjectMapper.toDto(curriculumSubject);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCurriculumSubjectMockMvc.perform(put("/api/curriculum-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumSubjectDTO)))
            .andExpect(status().isCreated());

        // Validate the CurriculumSubject in the database
        List<CurriculumSubject> curriculumSubjectList = curriculumSubjectRepository.findAll();
        assertThat(curriculumSubjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCurriculumSubject() throws Exception {
        // Initialize the database
        curriculumSubjectRepository.saveAndFlush(curriculumSubject);
        curriculumSubjectSearchRepository.save(curriculumSubject);
        int databaseSizeBeforeDelete = curriculumSubjectRepository.findAll().size();

        // Get the curriculumSubject
        restCurriculumSubjectMockMvc.perform(delete("/api/curriculum-subjects/{id}", curriculumSubject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean curriculumSubjectExistsInEs = curriculumSubjectSearchRepository.exists(curriculumSubject.getId());
        assertThat(curriculumSubjectExistsInEs).isFalse();

        // Validate the database is empty
        List<CurriculumSubject> curriculumSubjectList = curriculumSubjectRepository.findAll();
        assertThat(curriculumSubjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCurriculumSubject() throws Exception {
        // Initialize the database
        curriculumSubjectRepository.saveAndFlush(curriculumSubject);
        curriculumSubjectSearchRepository.save(curriculumSubject);

        // Search the curriculumSubject
        restCurriculumSubjectMockMvc.perform(get("/api/_search/curriculum-subjects?query=id:" + curriculumSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curriculumSubject.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurriculumSubject.class);
        CurriculumSubject curriculumSubject1 = new CurriculumSubject();
        curriculumSubject1.setId(1L);
        CurriculumSubject curriculumSubject2 = new CurriculumSubject();
        curriculumSubject2.setId(curriculumSubject1.getId());
        assertThat(curriculumSubject1).isEqualTo(curriculumSubject2);
        curriculumSubject2.setId(2L);
        assertThat(curriculumSubject1).isNotEqualTo(curriculumSubject2);
        curriculumSubject1.setId(null);
        assertThat(curriculumSubject1).isNotEqualTo(curriculumSubject2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurriculumSubjectDTO.class);
        CurriculumSubjectDTO curriculumSubjectDTO1 = new CurriculumSubjectDTO();
        curriculumSubjectDTO1.setId(1L);
        CurriculumSubjectDTO curriculumSubjectDTO2 = new CurriculumSubjectDTO();
        assertThat(curriculumSubjectDTO1).isNotEqualTo(curriculumSubjectDTO2);
        curriculumSubjectDTO2.setId(curriculumSubjectDTO1.getId());
        assertThat(curriculumSubjectDTO1).isEqualTo(curriculumSubjectDTO2);
        curriculumSubjectDTO2.setId(2L);
        assertThat(curriculumSubjectDTO1).isNotEqualTo(curriculumSubjectDTO2);
        curriculumSubjectDTO1.setId(null);
        assertThat(curriculumSubjectDTO1).isNotEqualTo(curriculumSubjectDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(curriculumSubjectMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(curriculumSubjectMapper.fromId(null)).isNull();
    }
}
