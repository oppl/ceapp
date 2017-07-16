package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.CollisionLevelOne;
import at.meroff.itproject.repository.CollisionLevelOneRepository;
import at.meroff.itproject.service.CollisionLevelOneService;
import at.meroff.itproject.repository.search.CollisionLevelOneSearchRepository;
import at.meroff.itproject.service.dto.CollisionLevelOneDTO;
import at.meroff.itproject.service.mapper.CollisionLevelOneMapper;
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
 * Test class for the CollisionLevelOneResource REST controller.
 *
 * @see CollisionLevelOneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class CollisionLevelOneResourceIntTest {

    private static final Integer DEFAULT_EXAM_COLLISION = 1;
    private static final Integer UPDATED_EXAM_COLLISION = 2;

    @Autowired
    private CollisionLevelOneRepository collisionLevelOneRepository;

    @Autowired
    private CollisionLevelOneMapper collisionLevelOneMapper;

    @Autowired
    private CollisionLevelOneService collisionLevelOneService;

    @Autowired
    private CollisionLevelOneSearchRepository collisionLevelOneSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollisionLevelOneMockMvc;

    private CollisionLevelOne collisionLevelOne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CollisionLevelOneResource collisionLevelOneResource = new CollisionLevelOneResource(collisionLevelOneService);
        this.restCollisionLevelOneMockMvc = MockMvcBuilders.standaloneSetup(collisionLevelOneResource)
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
    public static CollisionLevelOne createEntity(EntityManager em) {
        CollisionLevelOne collisionLevelOne = new CollisionLevelOne()
            .examCollision(DEFAULT_EXAM_COLLISION);
        return collisionLevelOne;
    }

    @Before
    public void initTest() {
        collisionLevelOneSearchRepository.deleteAll();
        collisionLevelOne = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollisionLevelOne() throws Exception {
        int databaseSizeBeforeCreate = collisionLevelOneRepository.findAll().size();

        // Create the CollisionLevelOne
        CollisionLevelOneDTO collisionLevelOneDTO = collisionLevelOneMapper.toDto(collisionLevelOne);
        restCollisionLevelOneMockMvc.perform(post("/api/collision-level-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelOneDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionLevelOne in the database
        List<CollisionLevelOne> collisionLevelOneList = collisionLevelOneRepository.findAll();
        assertThat(collisionLevelOneList).hasSize(databaseSizeBeforeCreate + 1);
        CollisionLevelOne testCollisionLevelOne = collisionLevelOneList.get(collisionLevelOneList.size() - 1);
        assertThat(testCollisionLevelOne.getExamCollision()).isEqualTo(DEFAULT_EXAM_COLLISION);

        // Validate the CollisionLevelOne in Elasticsearch
        CollisionLevelOne collisionLevelOneEs = collisionLevelOneSearchRepository.findOne(testCollisionLevelOne.getId());
        assertThat(collisionLevelOneEs).isEqualToComparingFieldByField(testCollisionLevelOne);
    }

    @Test
    @Transactional
    public void createCollisionLevelOneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collisionLevelOneRepository.findAll().size();

        // Create the CollisionLevelOne with an existing ID
        collisionLevelOne.setId(1L);
        CollisionLevelOneDTO collisionLevelOneDTO = collisionLevelOneMapper.toDto(collisionLevelOne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollisionLevelOneMockMvc.perform(post("/api/collision-level-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelOneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CollisionLevelOne> collisionLevelOneList = collisionLevelOneRepository.findAll();
        assertThat(collisionLevelOneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCollisionLevelOnes() throws Exception {
        // Initialize the database
        collisionLevelOneRepository.saveAndFlush(collisionLevelOne);

        // Get all the collisionLevelOneList
        restCollisionLevelOneMockMvc.perform(get("/api/collision-level-ones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionLevelOne.getId().intValue())))
            .andExpect(jsonPath("$.[*].examCollision").value(hasItem(DEFAULT_EXAM_COLLISION)));
    }

    @Test
    @Transactional
    public void getCollisionLevelOne() throws Exception {
        // Initialize the database
        collisionLevelOneRepository.saveAndFlush(collisionLevelOne);

        // Get the collisionLevelOne
        restCollisionLevelOneMockMvc.perform(get("/api/collision-level-ones/{id}", collisionLevelOne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collisionLevelOne.getId().intValue()))
            .andExpect(jsonPath("$.examCollision").value(DEFAULT_EXAM_COLLISION));
    }

    @Test
    @Transactional
    public void getNonExistingCollisionLevelOne() throws Exception {
        // Get the collisionLevelOne
        restCollisionLevelOneMockMvc.perform(get("/api/collision-level-ones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollisionLevelOne() throws Exception {
        // Initialize the database
        collisionLevelOneRepository.saveAndFlush(collisionLevelOne);
        collisionLevelOneSearchRepository.save(collisionLevelOne);
        int databaseSizeBeforeUpdate = collisionLevelOneRepository.findAll().size();

        // Update the collisionLevelOne
        CollisionLevelOne updatedCollisionLevelOne = collisionLevelOneRepository.findOne(collisionLevelOne.getId());
        updatedCollisionLevelOne
            .examCollision(UPDATED_EXAM_COLLISION);
        CollisionLevelOneDTO collisionLevelOneDTO = collisionLevelOneMapper.toDto(updatedCollisionLevelOne);

        restCollisionLevelOneMockMvc.perform(put("/api/collision-level-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelOneDTO)))
            .andExpect(status().isOk());

        // Validate the CollisionLevelOne in the database
        List<CollisionLevelOne> collisionLevelOneList = collisionLevelOneRepository.findAll();
        assertThat(collisionLevelOneList).hasSize(databaseSizeBeforeUpdate);
        CollisionLevelOne testCollisionLevelOne = collisionLevelOneList.get(collisionLevelOneList.size() - 1);
        assertThat(testCollisionLevelOne.getExamCollision()).isEqualTo(UPDATED_EXAM_COLLISION);

        // Validate the CollisionLevelOne in Elasticsearch
        CollisionLevelOne collisionLevelOneEs = collisionLevelOneSearchRepository.findOne(testCollisionLevelOne.getId());
        assertThat(collisionLevelOneEs).isEqualToComparingFieldByField(testCollisionLevelOne);
    }

    @Test
    @Transactional
    public void updateNonExistingCollisionLevelOne() throws Exception {
        int databaseSizeBeforeUpdate = collisionLevelOneRepository.findAll().size();

        // Create the CollisionLevelOne
        CollisionLevelOneDTO collisionLevelOneDTO = collisionLevelOneMapper.toDto(collisionLevelOne);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollisionLevelOneMockMvc.perform(put("/api/collision-level-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelOneDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionLevelOne in the database
        List<CollisionLevelOne> collisionLevelOneList = collisionLevelOneRepository.findAll();
        assertThat(collisionLevelOneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollisionLevelOne() throws Exception {
        // Initialize the database
        collisionLevelOneRepository.saveAndFlush(collisionLevelOne);
        collisionLevelOneSearchRepository.save(collisionLevelOne);
        int databaseSizeBeforeDelete = collisionLevelOneRepository.findAll().size();

        // Get the collisionLevelOne
        restCollisionLevelOneMockMvc.perform(delete("/api/collision-level-ones/{id}", collisionLevelOne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean collisionLevelOneExistsInEs = collisionLevelOneSearchRepository.exists(collisionLevelOne.getId());
        assertThat(collisionLevelOneExistsInEs).isFalse();

        // Validate the database is empty
        List<CollisionLevelOne> collisionLevelOneList = collisionLevelOneRepository.findAll();
        assertThat(collisionLevelOneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCollisionLevelOne() throws Exception {
        // Initialize the database
        collisionLevelOneRepository.saveAndFlush(collisionLevelOne);
        collisionLevelOneSearchRepository.save(collisionLevelOne);

        // Search the collisionLevelOne
        restCollisionLevelOneMockMvc.perform(get("/api/_search/collision-level-ones?query=id:" + collisionLevelOne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionLevelOne.getId().intValue())))
            .andExpect(jsonPath("$.[*].examCollision").value(hasItem(DEFAULT_EXAM_COLLISION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionLevelOne.class);
        CollisionLevelOne collisionLevelOne1 = new CollisionLevelOne();
        collisionLevelOne1.setId(1L);
        CollisionLevelOne collisionLevelOne2 = new CollisionLevelOne();
        collisionLevelOne2.setId(collisionLevelOne1.getId());
        assertThat(collisionLevelOne1).isEqualTo(collisionLevelOne2);
        collisionLevelOne2.setId(2L);
        assertThat(collisionLevelOne1).isNotEqualTo(collisionLevelOne2);
        collisionLevelOne1.setId(null);
        assertThat(collisionLevelOne1).isNotEqualTo(collisionLevelOne2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionLevelOneDTO.class);
        CollisionLevelOneDTO collisionLevelOneDTO1 = new CollisionLevelOneDTO();
        collisionLevelOneDTO1.setId(1L);
        CollisionLevelOneDTO collisionLevelOneDTO2 = new CollisionLevelOneDTO();
        assertThat(collisionLevelOneDTO1).isNotEqualTo(collisionLevelOneDTO2);
        collisionLevelOneDTO2.setId(collisionLevelOneDTO1.getId());
        assertThat(collisionLevelOneDTO1).isEqualTo(collisionLevelOneDTO2);
        collisionLevelOneDTO2.setId(2L);
        assertThat(collisionLevelOneDTO1).isNotEqualTo(collisionLevelOneDTO2);
        collisionLevelOneDTO1.setId(null);
        assertThat(collisionLevelOneDTO1).isNotEqualTo(collisionLevelOneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(collisionLevelOneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(collisionLevelOneMapper.fromId(null)).isNull();
    }
}
