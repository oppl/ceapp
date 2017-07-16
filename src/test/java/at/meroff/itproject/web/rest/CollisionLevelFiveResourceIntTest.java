package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.CollisionLevelFive;
import at.meroff.itproject.repository.CollisionLevelFiveRepository;
import at.meroff.itproject.service.CollisionLevelFiveService;
import at.meroff.itproject.repository.search.CollisionLevelFiveSearchRepository;
import at.meroff.itproject.service.dto.CollisionLevelFiveDTO;
import at.meroff.itproject.service.mapper.CollisionLevelFiveMapper;
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
 * Test class for the CollisionLevelFiveResource REST controller.
 *
 * @see CollisionLevelFiveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class CollisionLevelFiveResourceIntTest {

    private static final Integer DEFAULT_EXAM_COLLISION = 1;
    private static final Integer UPDATED_EXAM_COLLISION = 2;

    private static final Double DEFAULT_COLLISION_VALUE = 1D;
    private static final Double UPDATED_COLLISION_VALUE = 2D;

    @Autowired
    private CollisionLevelFiveRepository collisionLevelFiveRepository;

    @Autowired
    private CollisionLevelFiveMapper collisionLevelFiveMapper;

    @Autowired
    private CollisionLevelFiveService collisionLevelFiveService;

    @Autowired
    private CollisionLevelFiveSearchRepository collisionLevelFiveSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollisionLevelFiveMockMvc;

    private CollisionLevelFive collisionLevelFive;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CollisionLevelFiveResource collisionLevelFiveResource = new CollisionLevelFiveResource(collisionLevelFiveService);
        this.restCollisionLevelFiveMockMvc = MockMvcBuilders.standaloneSetup(collisionLevelFiveResource)
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
    public static CollisionLevelFive createEntity(EntityManager em) {
        CollisionLevelFive collisionLevelFive = new CollisionLevelFive()
            .examCollision(DEFAULT_EXAM_COLLISION)
            .collisionValue(DEFAULT_COLLISION_VALUE);
        return collisionLevelFive;
    }

    @Before
    public void initTest() {
        collisionLevelFiveSearchRepository.deleteAll();
        collisionLevelFive = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollisionLevelFive() throws Exception {
        int databaseSizeBeforeCreate = collisionLevelFiveRepository.findAll().size();

        // Create the CollisionLevelFive
        CollisionLevelFiveDTO collisionLevelFiveDTO = collisionLevelFiveMapper.toDto(collisionLevelFive);
        restCollisionLevelFiveMockMvc.perform(post("/api/collision-level-fives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelFiveDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionLevelFive in the database
        List<CollisionLevelFive> collisionLevelFiveList = collisionLevelFiveRepository.findAll();
        assertThat(collisionLevelFiveList).hasSize(databaseSizeBeforeCreate + 1);
        CollisionLevelFive testCollisionLevelFive = collisionLevelFiveList.get(collisionLevelFiveList.size() - 1);
        assertThat(testCollisionLevelFive.getExamCollision()).isEqualTo(DEFAULT_EXAM_COLLISION);
        assertThat(testCollisionLevelFive.getCollisionValue()).isEqualTo(DEFAULT_COLLISION_VALUE);

        // Validate the CollisionLevelFive in Elasticsearch
        CollisionLevelFive collisionLevelFiveEs = collisionLevelFiveSearchRepository.findOne(testCollisionLevelFive.getId());
        assertThat(collisionLevelFiveEs).isEqualToComparingFieldByField(testCollisionLevelFive);
    }

    @Test
    @Transactional
    public void createCollisionLevelFiveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collisionLevelFiveRepository.findAll().size();

        // Create the CollisionLevelFive with an existing ID
        collisionLevelFive.setId(1L);
        CollisionLevelFiveDTO collisionLevelFiveDTO = collisionLevelFiveMapper.toDto(collisionLevelFive);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollisionLevelFiveMockMvc.perform(post("/api/collision-level-fives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelFiveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CollisionLevelFive> collisionLevelFiveList = collisionLevelFiveRepository.findAll();
        assertThat(collisionLevelFiveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCollisionLevelFives() throws Exception {
        // Initialize the database
        collisionLevelFiveRepository.saveAndFlush(collisionLevelFive);

        // Get all the collisionLevelFiveList
        restCollisionLevelFiveMockMvc.perform(get("/api/collision-level-fives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionLevelFive.getId().intValue())))
            .andExpect(jsonPath("$.[*].examCollision").value(hasItem(DEFAULT_EXAM_COLLISION)))
            .andExpect(jsonPath("$.[*].collisionValue").value(hasItem(DEFAULT_COLLISION_VALUE.doubleValue())));
    }

    @Test
    @Transactional
    public void getCollisionLevelFive() throws Exception {
        // Initialize the database
        collisionLevelFiveRepository.saveAndFlush(collisionLevelFive);

        // Get the collisionLevelFive
        restCollisionLevelFiveMockMvc.perform(get("/api/collision-level-fives/{id}", collisionLevelFive.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collisionLevelFive.getId().intValue()))
            .andExpect(jsonPath("$.examCollision").value(DEFAULT_EXAM_COLLISION))
            .andExpect(jsonPath("$.collisionValue").value(DEFAULT_COLLISION_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCollisionLevelFive() throws Exception {
        // Get the collisionLevelFive
        restCollisionLevelFiveMockMvc.perform(get("/api/collision-level-fives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollisionLevelFive() throws Exception {
        // Initialize the database
        collisionLevelFiveRepository.saveAndFlush(collisionLevelFive);
        collisionLevelFiveSearchRepository.save(collisionLevelFive);
        int databaseSizeBeforeUpdate = collisionLevelFiveRepository.findAll().size();

        // Update the collisionLevelFive
        CollisionLevelFive updatedCollisionLevelFive = collisionLevelFiveRepository.findOne(collisionLevelFive.getId());
        updatedCollisionLevelFive
            .examCollision(UPDATED_EXAM_COLLISION)
            .collisionValue(UPDATED_COLLISION_VALUE);
        CollisionLevelFiveDTO collisionLevelFiveDTO = collisionLevelFiveMapper.toDto(updatedCollisionLevelFive);

        restCollisionLevelFiveMockMvc.perform(put("/api/collision-level-fives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelFiveDTO)))
            .andExpect(status().isOk());

        // Validate the CollisionLevelFive in the database
        List<CollisionLevelFive> collisionLevelFiveList = collisionLevelFiveRepository.findAll();
        assertThat(collisionLevelFiveList).hasSize(databaseSizeBeforeUpdate);
        CollisionLevelFive testCollisionLevelFive = collisionLevelFiveList.get(collisionLevelFiveList.size() - 1);
        assertThat(testCollisionLevelFive.getExamCollision()).isEqualTo(UPDATED_EXAM_COLLISION);
        assertThat(testCollisionLevelFive.getCollisionValue()).isEqualTo(UPDATED_COLLISION_VALUE);

        // Validate the CollisionLevelFive in Elasticsearch
        CollisionLevelFive collisionLevelFiveEs = collisionLevelFiveSearchRepository.findOne(testCollisionLevelFive.getId());
        assertThat(collisionLevelFiveEs).isEqualToComparingFieldByField(testCollisionLevelFive);
    }

    @Test
    @Transactional
    public void updateNonExistingCollisionLevelFive() throws Exception {
        int databaseSizeBeforeUpdate = collisionLevelFiveRepository.findAll().size();

        // Create the CollisionLevelFive
        CollisionLevelFiveDTO collisionLevelFiveDTO = collisionLevelFiveMapper.toDto(collisionLevelFive);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollisionLevelFiveMockMvc.perform(put("/api/collision-level-fives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelFiveDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionLevelFive in the database
        List<CollisionLevelFive> collisionLevelFiveList = collisionLevelFiveRepository.findAll();
        assertThat(collisionLevelFiveList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollisionLevelFive() throws Exception {
        // Initialize the database
        collisionLevelFiveRepository.saveAndFlush(collisionLevelFive);
        collisionLevelFiveSearchRepository.save(collisionLevelFive);
        int databaseSizeBeforeDelete = collisionLevelFiveRepository.findAll().size();

        // Get the collisionLevelFive
        restCollisionLevelFiveMockMvc.perform(delete("/api/collision-level-fives/{id}", collisionLevelFive.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean collisionLevelFiveExistsInEs = collisionLevelFiveSearchRepository.exists(collisionLevelFive.getId());
        assertThat(collisionLevelFiveExistsInEs).isFalse();

        // Validate the database is empty
        List<CollisionLevelFive> collisionLevelFiveList = collisionLevelFiveRepository.findAll();
        assertThat(collisionLevelFiveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCollisionLevelFive() throws Exception {
        // Initialize the database
        collisionLevelFiveRepository.saveAndFlush(collisionLevelFive);
        collisionLevelFiveSearchRepository.save(collisionLevelFive);

        // Search the collisionLevelFive
        restCollisionLevelFiveMockMvc.perform(get("/api/_search/collision-level-fives?query=id:" + collisionLevelFive.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionLevelFive.getId().intValue())))
            .andExpect(jsonPath("$.[*].examCollision").value(hasItem(DEFAULT_EXAM_COLLISION)))
            .andExpect(jsonPath("$.[*].collisionValue").value(hasItem(DEFAULT_COLLISION_VALUE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionLevelFive.class);
        CollisionLevelFive collisionLevelFive1 = new CollisionLevelFive();
        collisionLevelFive1.setId(1L);
        CollisionLevelFive collisionLevelFive2 = new CollisionLevelFive();
        collisionLevelFive2.setId(collisionLevelFive1.getId());
        assertThat(collisionLevelFive1).isEqualTo(collisionLevelFive2);
        collisionLevelFive2.setId(2L);
        assertThat(collisionLevelFive1).isNotEqualTo(collisionLevelFive2);
        collisionLevelFive1.setId(null);
        assertThat(collisionLevelFive1).isNotEqualTo(collisionLevelFive2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionLevelFiveDTO.class);
        CollisionLevelFiveDTO collisionLevelFiveDTO1 = new CollisionLevelFiveDTO();
        collisionLevelFiveDTO1.setId(1L);
        CollisionLevelFiveDTO collisionLevelFiveDTO2 = new CollisionLevelFiveDTO();
        assertThat(collisionLevelFiveDTO1).isNotEqualTo(collisionLevelFiveDTO2);
        collisionLevelFiveDTO2.setId(collisionLevelFiveDTO1.getId());
        assertThat(collisionLevelFiveDTO1).isEqualTo(collisionLevelFiveDTO2);
        collisionLevelFiveDTO2.setId(2L);
        assertThat(collisionLevelFiveDTO1).isNotEqualTo(collisionLevelFiveDTO2);
        collisionLevelFiveDTO1.setId(null);
        assertThat(collisionLevelFiveDTO1).isNotEqualTo(collisionLevelFiveDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(collisionLevelFiveMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(collisionLevelFiveMapper.fromId(null)).isNull();
    }
}
