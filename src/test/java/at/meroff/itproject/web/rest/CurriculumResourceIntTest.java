package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.Curriculum;
import at.meroff.itproject.repository.CurriculumRepository;
import at.meroff.itproject.service.CurriculumService;
import at.meroff.itproject.service.dto.CurriculumDTO;
import at.meroff.itproject.service.mapper.CurriculumMapper;
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
 * Test class for the CurriculumResource REST controller.
 *
 * @see CurriculumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class CurriculumResourceIntTest {

    private static final Integer DEFAULT_CUR_ID = 1;
    private static final Integer UPDATED_CUR_ID = 2;

    private static final String DEFAULT_CUR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUR_NAME = "BBBBBBBBBB";

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private CurriculumMapper curriculumMapper;

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCurriculumMockMvc;

    private Curriculum curriculum;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CurriculumResource curriculumResource = new CurriculumResource(curriculumService);
        this.restCurriculumMockMvc = MockMvcBuilders.standaloneSetup(curriculumResource)
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
    public static Curriculum createEntity(EntityManager em) {
        Curriculum curriculum = new Curriculum()
            .curId(DEFAULT_CUR_ID)
            .curName(DEFAULT_CUR_NAME);
        return curriculum;
    }

    @Before
    public void initTest() {
        curriculum = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurriculum() throws Exception {
        int databaseSizeBeforeCreate = curriculumRepository.findAll().size();

        // Create the Curriculum
        CurriculumDTO curriculumDTO = curriculumMapper.toDto(curriculum);
        restCurriculumMockMvc.perform(post("/api/curricula")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumDTO)))
            .andExpect(status().isCreated());

        // Validate the Curriculum in the database
        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeCreate + 1);
        Curriculum testCurriculum = curriculumList.get(curriculumList.size() - 1);
        assertThat(testCurriculum.getCurId()).isEqualTo(DEFAULT_CUR_ID);
        assertThat(testCurriculum.getCurName()).isEqualTo(DEFAULT_CUR_NAME);
    }

    @Test
    @Transactional
    public void createCurriculumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = curriculumRepository.findAll().size();

        // Create the Curriculum with an existing ID
        curriculum.setId(1L);
        CurriculumDTO curriculumDTO = curriculumMapper.toDto(curriculum);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurriculumMockMvc.perform(post("/api/curricula")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCurIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = curriculumRepository.findAll().size();
        // set the field null
        curriculum.setCurId(null);

        // Create the Curriculum, which fails.
        CurriculumDTO curriculumDTO = curriculumMapper.toDto(curriculum);

        restCurriculumMockMvc.perform(post("/api/curricula")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumDTO)))
            .andExpect(status().isBadRequest());

        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = curriculumRepository.findAll().size();
        // set the field null
        curriculum.setCurName(null);

        // Create the Curriculum, which fails.
        CurriculumDTO curriculumDTO = curriculumMapper.toDto(curriculum);

        restCurriculumMockMvc.perform(post("/api/curricula")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumDTO)))
            .andExpect(status().isBadRequest());

        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurricula() throws Exception {
        // Initialize the database
        curriculumRepository.saveAndFlush(curriculum);

        // Get all the curriculumList
        restCurriculumMockMvc.perform(get("/api/curricula?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curriculum.getId().intValue())))
            .andExpect(jsonPath("$.[*].curId").value(hasItem(DEFAULT_CUR_ID)))
            .andExpect(jsonPath("$.[*].curName").value(hasItem(DEFAULT_CUR_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCurriculum() throws Exception {
        // Initialize the database
        curriculumRepository.saveAndFlush(curriculum);

        // Get the curriculum
        restCurriculumMockMvc.perform(get("/api/curricula/{id}", curriculum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(curriculum.getId().intValue()))
            .andExpect(jsonPath("$.curId").value(DEFAULT_CUR_ID))
            .andExpect(jsonPath("$.curName").value(DEFAULT_CUR_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCurriculum() throws Exception {
        // Get the curriculum
        restCurriculumMockMvc.perform(get("/api/curricula/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurriculum() throws Exception {
        // Initialize the database
        curriculumRepository.saveAndFlush(curriculum);
        int databaseSizeBeforeUpdate = curriculumRepository.findAll().size();

        // Update the curriculum
        Curriculum updatedCurriculum = curriculumRepository.findOne(curriculum.getId());
        updatedCurriculum
            .curId(UPDATED_CUR_ID)
            .curName(UPDATED_CUR_NAME);
        CurriculumDTO curriculumDTO = curriculumMapper.toDto(updatedCurriculum);

        restCurriculumMockMvc.perform(put("/api/curricula")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumDTO)))
            .andExpect(status().isOk());

        // Validate the Curriculum in the database
        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeUpdate);
        Curriculum testCurriculum = curriculumList.get(curriculumList.size() - 1);
        assertThat(testCurriculum.getCurId()).isEqualTo(UPDATED_CUR_ID);
        assertThat(testCurriculum.getCurName()).isEqualTo(UPDATED_CUR_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCurriculum() throws Exception {
        int databaseSizeBeforeUpdate = curriculumRepository.findAll().size();

        // Create the Curriculum
        CurriculumDTO curriculumDTO = curriculumMapper.toDto(curriculum);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCurriculumMockMvc.perform(put("/api/curricula")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumDTO)))
            .andExpect(status().isCreated());

        // Validate the Curriculum in the database
        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCurriculum() throws Exception {
        // Initialize the database
        curriculumRepository.saveAndFlush(curriculum);
        int databaseSizeBeforeDelete = curriculumRepository.findAll().size();

        // Get the curriculum
        restCurriculumMockMvc.perform(delete("/api/curricula/{id}", curriculum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Curriculum.class);
        Curriculum curriculum1 = new Curriculum();
        curriculum1.setId(1L);
        Curriculum curriculum2 = new Curriculum();
        curriculum2.setId(curriculum1.getId());
        assertThat(curriculum1).isEqualTo(curriculum2);
        curriculum2.setId(2L);
        assertThat(curriculum1).isNotEqualTo(curriculum2);
        curriculum1.setId(null);
        assertThat(curriculum1).isNotEqualTo(curriculum2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurriculumDTO.class);
        CurriculumDTO curriculumDTO1 = new CurriculumDTO();
        curriculumDTO1.setId(1L);
        CurriculumDTO curriculumDTO2 = new CurriculumDTO();
        assertThat(curriculumDTO1).isNotEqualTo(curriculumDTO2);
        curriculumDTO2.setId(curriculumDTO1.getId());
        assertThat(curriculumDTO1).isEqualTo(curriculumDTO2);
        curriculumDTO2.setId(2L);
        assertThat(curriculumDTO1).isNotEqualTo(curriculumDTO2);
        curriculumDTO1.setId(null);
        assertThat(curriculumDTO1).isNotEqualTo(curriculumDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(curriculumMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(curriculumMapper.fromId(null)).isNull();
    }
}
