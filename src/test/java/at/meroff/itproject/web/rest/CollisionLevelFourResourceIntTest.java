package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.CollisionLevelFour;
import at.meroff.itproject.repository.CollisionLevelFourRepository;
import at.meroff.itproject.service.CollisionLevelFourService;
import at.meroff.itproject.repository.search.CollisionLevelFourSearchRepository;
import at.meroff.itproject.service.dto.CollisionLevelFourDTO;
import at.meroff.itproject.service.mapper.CollisionLevelFourMapper;
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

import at.meroff.itproject.domain.enumeration.CollisionType;
/**
 * Test class for the CollisionLevelFourResource REST controller.
 *
 * @see CollisionLevelFourResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class CollisionLevelFourResourceIntTest {

    private static final Integer DEFAULT_EXAM_COLLISION = 1;
    private static final Integer UPDATED_EXAM_COLLISION = 2;

    private static final Integer DEFAULT_INSTITUTE_COLLISION = 1;
    private static final Integer UPDATED_INSTITUTE_COLLISION = 2;

    private static final Integer DEFAULT_CURRICULUM_COLLISION = 1;
    private static final Integer UPDATED_CURRICULUM_COLLISION = 2;

    private static final CollisionType DEFAULT_COLLISION_TYPE = CollisionType.INST_INST;
    private static final CollisionType UPDATED_COLLISION_TYPE = CollisionType.WIN_WIN;

    @Autowired
    private CollisionLevelFourRepository collisionLevelFourRepository;

    @Autowired
    private CollisionLevelFourMapper collisionLevelFourMapper;

    @Autowired
    private CollisionLevelFourService collisionLevelFourService;

    @Autowired
    private CollisionLevelFourSearchRepository collisionLevelFourSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollisionLevelFourMockMvc;

    private CollisionLevelFour collisionLevelFour;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CollisionLevelFourResource collisionLevelFourResource = new CollisionLevelFourResource(collisionLevelFourService);
        this.restCollisionLevelFourMockMvc = MockMvcBuilders.standaloneSetup(collisionLevelFourResource)
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
    public static CollisionLevelFour createEntity(EntityManager em) {
        CollisionLevelFour collisionLevelFour = new CollisionLevelFour()
            .examCollision(DEFAULT_EXAM_COLLISION)
            .instituteCollision(DEFAULT_INSTITUTE_COLLISION)
            .curriculumCollision(DEFAULT_CURRICULUM_COLLISION)
            .collisionType(DEFAULT_COLLISION_TYPE);
        return collisionLevelFour;
    }

    @Before
    public void initTest() {
        collisionLevelFourSearchRepository.deleteAll();
        collisionLevelFour = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollisionLevelFour() throws Exception {
        int databaseSizeBeforeCreate = collisionLevelFourRepository.findAll().size();

        // Create the CollisionLevelFour
        CollisionLevelFourDTO collisionLevelFourDTO = collisionLevelFourMapper.toDto(collisionLevelFour);
        restCollisionLevelFourMockMvc.perform(post("/api/collision-level-fours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelFourDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionLevelFour in the database
        List<CollisionLevelFour> collisionLevelFourList = collisionLevelFourRepository.findAll();
        assertThat(collisionLevelFourList).hasSize(databaseSizeBeforeCreate + 1);
        CollisionLevelFour testCollisionLevelFour = collisionLevelFourList.get(collisionLevelFourList.size() - 1);
        assertThat(testCollisionLevelFour.getExamCollision()).isEqualTo(DEFAULT_EXAM_COLLISION);
        assertThat(testCollisionLevelFour.getInstituteCollision()).isEqualTo(DEFAULT_INSTITUTE_COLLISION);
        assertThat(testCollisionLevelFour.getCurriculumCollision()).isEqualTo(DEFAULT_CURRICULUM_COLLISION);
        assertThat(testCollisionLevelFour.getCollisionType()).isEqualTo(DEFAULT_COLLISION_TYPE);

        // Validate the CollisionLevelFour in Elasticsearch
        CollisionLevelFour collisionLevelFourEs = collisionLevelFourSearchRepository.findOne(testCollisionLevelFour.getId());
        assertThat(collisionLevelFourEs).isEqualToComparingFieldByField(testCollisionLevelFour);
    }

    @Test
    @Transactional
    public void createCollisionLevelFourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collisionLevelFourRepository.findAll().size();

        // Create the CollisionLevelFour with an existing ID
        collisionLevelFour.setId(1L);
        CollisionLevelFourDTO collisionLevelFourDTO = collisionLevelFourMapper.toDto(collisionLevelFour);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollisionLevelFourMockMvc.perform(post("/api/collision-level-fours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelFourDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CollisionLevelFour> collisionLevelFourList = collisionLevelFourRepository.findAll();
        assertThat(collisionLevelFourList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCollisionLevelFours() throws Exception {
        // Initialize the database
        collisionLevelFourRepository.saveAndFlush(collisionLevelFour);

        // Get all the collisionLevelFourList
        restCollisionLevelFourMockMvc.perform(get("/api/collision-level-fours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionLevelFour.getId().intValue())))
            .andExpect(jsonPath("$.[*].examCollision").value(hasItem(DEFAULT_EXAM_COLLISION)))
            .andExpect(jsonPath("$.[*].instituteCollision").value(hasItem(DEFAULT_INSTITUTE_COLLISION)))
            .andExpect(jsonPath("$.[*].curriculumCollision").value(hasItem(DEFAULT_CURRICULUM_COLLISION)))
            .andExpect(jsonPath("$.[*].collisionType").value(hasItem(DEFAULT_COLLISION_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCollisionLevelFour() throws Exception {
        // Initialize the database
        collisionLevelFourRepository.saveAndFlush(collisionLevelFour);

        // Get the collisionLevelFour
        restCollisionLevelFourMockMvc.perform(get("/api/collision-level-fours/{id}", collisionLevelFour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collisionLevelFour.getId().intValue()))
            .andExpect(jsonPath("$.examCollision").value(DEFAULT_EXAM_COLLISION))
            .andExpect(jsonPath("$.instituteCollision").value(DEFAULT_INSTITUTE_COLLISION))
            .andExpect(jsonPath("$.curriculumCollision").value(DEFAULT_CURRICULUM_COLLISION))
            .andExpect(jsonPath("$.collisionType").value(DEFAULT_COLLISION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCollisionLevelFour() throws Exception {
        // Get the collisionLevelFour
        restCollisionLevelFourMockMvc.perform(get("/api/collision-level-fours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollisionLevelFour() throws Exception {
        // Initialize the database
        collisionLevelFourRepository.saveAndFlush(collisionLevelFour);
        collisionLevelFourSearchRepository.save(collisionLevelFour);
        int databaseSizeBeforeUpdate = collisionLevelFourRepository.findAll().size();

        // Update the collisionLevelFour
        CollisionLevelFour updatedCollisionLevelFour = collisionLevelFourRepository.findOne(collisionLevelFour.getId());
        updatedCollisionLevelFour
            .examCollision(UPDATED_EXAM_COLLISION)
            .instituteCollision(UPDATED_INSTITUTE_COLLISION)
            .curriculumCollision(UPDATED_CURRICULUM_COLLISION)
            .collisionType(UPDATED_COLLISION_TYPE);
        CollisionLevelFourDTO collisionLevelFourDTO = collisionLevelFourMapper.toDto(updatedCollisionLevelFour);

        restCollisionLevelFourMockMvc.perform(put("/api/collision-level-fours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelFourDTO)))
            .andExpect(status().isOk());

        // Validate the CollisionLevelFour in the database
        List<CollisionLevelFour> collisionLevelFourList = collisionLevelFourRepository.findAll();
        assertThat(collisionLevelFourList).hasSize(databaseSizeBeforeUpdate);
        CollisionLevelFour testCollisionLevelFour = collisionLevelFourList.get(collisionLevelFourList.size() - 1);
        assertThat(testCollisionLevelFour.getExamCollision()).isEqualTo(UPDATED_EXAM_COLLISION);
        assertThat(testCollisionLevelFour.getInstituteCollision()).isEqualTo(UPDATED_INSTITUTE_COLLISION);
        assertThat(testCollisionLevelFour.getCurriculumCollision()).isEqualTo(UPDATED_CURRICULUM_COLLISION);
        assertThat(testCollisionLevelFour.getCollisionType()).isEqualTo(UPDATED_COLLISION_TYPE);

        // Validate the CollisionLevelFour in Elasticsearch
        CollisionLevelFour collisionLevelFourEs = collisionLevelFourSearchRepository.findOne(testCollisionLevelFour.getId());
        assertThat(collisionLevelFourEs).isEqualToComparingFieldByField(testCollisionLevelFour);
    }

    @Test
    @Transactional
    public void updateNonExistingCollisionLevelFour() throws Exception {
        int databaseSizeBeforeUpdate = collisionLevelFourRepository.findAll().size();

        // Create the CollisionLevelFour
        CollisionLevelFourDTO collisionLevelFourDTO = collisionLevelFourMapper.toDto(collisionLevelFour);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollisionLevelFourMockMvc.perform(put("/api/collision-level-fours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelFourDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionLevelFour in the database
        List<CollisionLevelFour> collisionLevelFourList = collisionLevelFourRepository.findAll();
        assertThat(collisionLevelFourList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollisionLevelFour() throws Exception {
        // Initialize the database
        collisionLevelFourRepository.saveAndFlush(collisionLevelFour);
        collisionLevelFourSearchRepository.save(collisionLevelFour);
        int databaseSizeBeforeDelete = collisionLevelFourRepository.findAll().size();

        // Get the collisionLevelFour
        restCollisionLevelFourMockMvc.perform(delete("/api/collision-level-fours/{id}", collisionLevelFour.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean collisionLevelFourExistsInEs = collisionLevelFourSearchRepository.exists(collisionLevelFour.getId());
        assertThat(collisionLevelFourExistsInEs).isFalse();

        // Validate the database is empty
        List<CollisionLevelFour> collisionLevelFourList = collisionLevelFourRepository.findAll();
        assertThat(collisionLevelFourList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCollisionLevelFour() throws Exception {
        // Initialize the database
        collisionLevelFourRepository.saveAndFlush(collisionLevelFour);
        collisionLevelFourSearchRepository.save(collisionLevelFour);

        // Search the collisionLevelFour
        restCollisionLevelFourMockMvc.perform(get("/api/_search/collision-level-fours?query=id:" + collisionLevelFour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionLevelFour.getId().intValue())))
            .andExpect(jsonPath("$.[*].examCollision").value(hasItem(DEFAULT_EXAM_COLLISION)))
            .andExpect(jsonPath("$.[*].instituteCollision").value(hasItem(DEFAULT_INSTITUTE_COLLISION)))
            .andExpect(jsonPath("$.[*].curriculumCollision").value(hasItem(DEFAULT_CURRICULUM_COLLISION)))
            .andExpect(jsonPath("$.[*].collisionType").value(hasItem(DEFAULT_COLLISION_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionLevelFour.class);
        CollisionLevelFour collisionLevelFour1 = new CollisionLevelFour();
        collisionLevelFour1.setId(1L);
        CollisionLevelFour collisionLevelFour2 = new CollisionLevelFour();
        collisionLevelFour2.setId(collisionLevelFour1.getId());
        assertThat(collisionLevelFour1).isEqualTo(collisionLevelFour2);
        collisionLevelFour2.setId(2L);
        assertThat(collisionLevelFour1).isNotEqualTo(collisionLevelFour2);
        collisionLevelFour1.setId(null);
        assertThat(collisionLevelFour1).isNotEqualTo(collisionLevelFour2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionLevelFourDTO.class);
        CollisionLevelFourDTO collisionLevelFourDTO1 = new CollisionLevelFourDTO();
        collisionLevelFourDTO1.setId(1L);
        CollisionLevelFourDTO collisionLevelFourDTO2 = new CollisionLevelFourDTO();
        assertThat(collisionLevelFourDTO1).isNotEqualTo(collisionLevelFourDTO2);
        collisionLevelFourDTO2.setId(collisionLevelFourDTO1.getId());
        assertThat(collisionLevelFourDTO1).isEqualTo(collisionLevelFourDTO2);
        collisionLevelFourDTO2.setId(2L);
        assertThat(collisionLevelFourDTO1).isNotEqualTo(collisionLevelFourDTO2);
        collisionLevelFourDTO1.setId(null);
        assertThat(collisionLevelFourDTO1).isNotEqualTo(collisionLevelFourDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(collisionLevelFourMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(collisionLevelFourMapper.fromId(null)).isNull();
    }
}
