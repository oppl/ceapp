package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.Lva;
import at.meroff.itproject.repository.LvaRepository;
import at.meroff.itproject.service.LvaService;
import at.meroff.itproject.repository.search.LvaSearchRepository;
import at.meroff.itproject.service.dto.LvaDTO;
import at.meroff.itproject.service.mapper.LvaMapper;
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

import at.meroff.itproject.domain.enumeration.LvaType;
import at.meroff.itproject.domain.enumeration.Semester;
/**
 * Test class for the LvaResource REST controller.
 *
 * @see LvaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class LvaResourceIntTest {

    private static final String DEFAULT_LVA_NR = "AAAAAAAAAA";
    private static final String UPDATED_LVA_NR = "BBBBBBBBBB";

    private static final LvaType DEFAULT_LVA_TYPE = LvaType.WEEKLY;
    private static final LvaType UPDATED_LVA_TYPE = LvaType.BLOCK;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Semester DEFAULT_SEMESTER = Semester.WS;
    private static final Semester UPDATED_SEMESTER = Semester.SS;

    private static final Integer DEFAULT_COUNT_APPOINTMENTS = 1;
    private static final Integer UPDATED_COUNT_APPOINTMENTS = 2;

    @Autowired
    private LvaRepository lvaRepository;

    @Autowired
    private LvaMapper lvaMapper;

    @Autowired
    private LvaService lvaService;

    @Autowired
    private LvaSearchRepository lvaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLvaMockMvc;

    private Lva lva;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LvaResource lvaResource = new LvaResource(lvaService);
        this.restLvaMockMvc = MockMvcBuilders.standaloneSetup(lvaResource)
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
    public static Lva createEntity(EntityManager em) {
        Lva lva = new Lva()
            .lvaNr(DEFAULT_LVA_NR)
            .lvaType(DEFAULT_LVA_TYPE)
            .year(DEFAULT_YEAR)
            .semester(DEFAULT_SEMESTER)
            .countAppointments(DEFAULT_COUNT_APPOINTMENTS);
        return lva;
    }

    @Before
    public void initTest() {
        lvaSearchRepository.deleteAll();
        lva = createEntity(em);
    }

    @Test
    @Transactional
    public void createLva() throws Exception {
        int databaseSizeBeforeCreate = lvaRepository.findAll().size();

        // Create the Lva
        LvaDTO lvaDTO = lvaMapper.toDto(lva);
        restLvaMockMvc.perform(post("/api/lvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lvaDTO)))
            .andExpect(status().isCreated());

        // Validate the Lva in the database
        List<Lva> lvaList = lvaRepository.findAll();
        assertThat(lvaList).hasSize(databaseSizeBeforeCreate + 1);
        Lva testLva = lvaList.get(lvaList.size() - 1);
        assertThat(testLva.getLvaNr()).isEqualTo(DEFAULT_LVA_NR);
        assertThat(testLva.getLvaType()).isEqualTo(DEFAULT_LVA_TYPE);
        assertThat(testLva.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLva.getSemester()).isEqualTo(DEFAULT_SEMESTER);
        assertThat(testLva.getCountAppointments()).isEqualTo(DEFAULT_COUNT_APPOINTMENTS);

        // Validate the Lva in Elasticsearch
        Lva lvaEs = lvaSearchRepository.findOne(testLva.getId());
        assertThat(lvaEs).isEqualToComparingFieldByField(testLva);
    }

    @Test
    @Transactional
    public void createLvaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lvaRepository.findAll().size();

        // Create the Lva with an existing ID
        lva.setId(1L);
        LvaDTO lvaDTO = lvaMapper.toDto(lva);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLvaMockMvc.perform(post("/api/lvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lvaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Lva> lvaList = lvaRepository.findAll();
        assertThat(lvaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLvaNrIsRequired() throws Exception {
        int databaseSizeBeforeTest = lvaRepository.findAll().size();
        // set the field null
        lva.setLvaNr(null);

        // Create the Lva, which fails.
        LvaDTO lvaDTO = lvaMapper.toDto(lva);

        restLvaMockMvc.perform(post("/api/lvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lvaDTO)))
            .andExpect(status().isBadRequest());

        List<Lva> lvaList = lvaRepository.findAll();
        assertThat(lvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLvaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lvaRepository.findAll().size();
        // set the field null
        lva.setLvaType(null);

        // Create the Lva, which fails.
        LvaDTO lvaDTO = lvaMapper.toDto(lva);

        restLvaMockMvc.perform(post("/api/lvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lvaDTO)))
            .andExpect(status().isBadRequest());

        List<Lva> lvaList = lvaRepository.findAll();
        assertThat(lvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = lvaRepository.findAll().size();
        // set the field null
        lva.setYear(null);

        // Create the Lva, which fails.
        LvaDTO lvaDTO = lvaMapper.toDto(lva);

        restLvaMockMvc.perform(post("/api/lvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lvaDTO)))
            .andExpect(status().isBadRequest());

        List<Lva> lvaList = lvaRepository.findAll();
        assertThat(lvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSemesterIsRequired() throws Exception {
        int databaseSizeBeforeTest = lvaRepository.findAll().size();
        // set the field null
        lva.setSemester(null);

        // Create the Lva, which fails.
        LvaDTO lvaDTO = lvaMapper.toDto(lva);

        restLvaMockMvc.perform(post("/api/lvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lvaDTO)))
            .andExpect(status().isBadRequest());

        List<Lva> lvaList = lvaRepository.findAll();
        assertThat(lvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLvas() throws Exception {
        // Initialize the database
        lvaRepository.saveAndFlush(lva);

        // Get all the lvaList
        restLvaMockMvc.perform(get("/api/lvas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lva.getId().intValue())))
            .andExpect(jsonPath("$.[*].lvaNr").value(hasItem(DEFAULT_LVA_NR.toString())))
            .andExpect(jsonPath("$.[*].lvaType").value(hasItem(DEFAULT_LVA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())))
            .andExpect(jsonPath("$.[*].countAppointments").value(hasItem(DEFAULT_COUNT_APPOINTMENTS)));
    }

    @Test
    @Transactional
    public void getLva() throws Exception {
        // Initialize the database
        lvaRepository.saveAndFlush(lva);

        // Get the lva
        restLvaMockMvc.perform(get("/api/lvas/{id}", lva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lva.getId().intValue()))
            .andExpect(jsonPath("$.lvaNr").value(DEFAULT_LVA_NR.toString()))
            .andExpect(jsonPath("$.lvaType").value(DEFAULT_LVA_TYPE.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()))
            .andExpect(jsonPath("$.countAppointments").value(DEFAULT_COUNT_APPOINTMENTS));
    }

    @Test
    @Transactional
    public void getNonExistingLva() throws Exception {
        // Get the lva
        restLvaMockMvc.perform(get("/api/lvas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLva() throws Exception {
        // Initialize the database
        lvaRepository.saveAndFlush(lva);
        lvaSearchRepository.save(lva);
        int databaseSizeBeforeUpdate = lvaRepository.findAll().size();

        // Update the lva
        Lva updatedLva = lvaRepository.findOne(lva.getId());
        updatedLva
            .lvaNr(UPDATED_LVA_NR)
            .lvaType(UPDATED_LVA_TYPE)
            .year(UPDATED_YEAR)
            .semester(UPDATED_SEMESTER)
            .countAppointments(UPDATED_COUNT_APPOINTMENTS);
        LvaDTO lvaDTO = lvaMapper.toDto(updatedLva);

        restLvaMockMvc.perform(put("/api/lvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lvaDTO)))
            .andExpect(status().isOk());

        // Validate the Lva in the database
        List<Lva> lvaList = lvaRepository.findAll();
        assertThat(lvaList).hasSize(databaseSizeBeforeUpdate);
        Lva testLva = lvaList.get(lvaList.size() - 1);
        assertThat(testLva.getLvaNr()).isEqualTo(UPDATED_LVA_NR);
        assertThat(testLva.getLvaType()).isEqualTo(UPDATED_LVA_TYPE);
        assertThat(testLva.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLva.getSemester()).isEqualTo(UPDATED_SEMESTER);
        assertThat(testLva.getCountAppointments()).isEqualTo(UPDATED_COUNT_APPOINTMENTS);

        // Validate the Lva in Elasticsearch
        Lva lvaEs = lvaSearchRepository.findOne(testLva.getId());
        assertThat(lvaEs).isEqualToComparingFieldByField(testLva);
    }

    @Test
    @Transactional
    public void updateNonExistingLva() throws Exception {
        int databaseSizeBeforeUpdate = lvaRepository.findAll().size();

        // Create the Lva
        LvaDTO lvaDTO = lvaMapper.toDto(lva);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLvaMockMvc.perform(put("/api/lvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lvaDTO)))
            .andExpect(status().isCreated());

        // Validate the Lva in the database
        List<Lva> lvaList = lvaRepository.findAll();
        assertThat(lvaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLva() throws Exception {
        // Initialize the database
        lvaRepository.saveAndFlush(lva);
        lvaSearchRepository.save(lva);
        int databaseSizeBeforeDelete = lvaRepository.findAll().size();

        // Get the lva
        restLvaMockMvc.perform(delete("/api/lvas/{id}", lva.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean lvaExistsInEs = lvaSearchRepository.exists(lva.getId());
        assertThat(lvaExistsInEs).isFalse();

        // Validate the database is empty
        List<Lva> lvaList = lvaRepository.findAll();
        assertThat(lvaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLva() throws Exception {
        // Initialize the database
        lvaRepository.saveAndFlush(lva);
        lvaSearchRepository.save(lva);

        // Search the lva
        restLvaMockMvc.perform(get("/api/_search/lvas?query=id:" + lva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lva.getId().intValue())))
            .andExpect(jsonPath("$.[*].lvaNr").value(hasItem(DEFAULT_LVA_NR.toString())))
            .andExpect(jsonPath("$.[*].lvaType").value(hasItem(DEFAULT_LVA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())))
            .andExpect(jsonPath("$.[*].countAppointments").value(hasItem(DEFAULT_COUNT_APPOINTMENTS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lva.class);
        Lva lva1 = new Lva();
        lva1.setId(1L);
        Lva lva2 = new Lva();
        lva2.setId(lva1.getId());
        assertThat(lva1).isEqualTo(lva2);
        lva2.setId(2L);
        assertThat(lva1).isNotEqualTo(lva2);
        lva1.setId(null);
        assertThat(lva1).isNotEqualTo(lva2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LvaDTO.class);
        LvaDTO lvaDTO1 = new LvaDTO();
        lvaDTO1.setId(1L);
        LvaDTO lvaDTO2 = new LvaDTO();
        assertThat(lvaDTO1).isNotEqualTo(lvaDTO2);
        lvaDTO2.setId(lvaDTO1.getId());
        assertThat(lvaDTO1).isEqualTo(lvaDTO2);
        lvaDTO2.setId(2L);
        assertThat(lvaDTO1).isNotEqualTo(lvaDTO2);
        lvaDTO1.setId(null);
        assertThat(lvaDTO1).isNotEqualTo(lvaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lvaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lvaMapper.fromId(null)).isNull();
    }
}
