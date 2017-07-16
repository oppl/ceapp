package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.CollisionLevelThree;
import at.meroff.itproject.repository.CollisionLevelThreeRepository;
import at.meroff.itproject.service.CollisionLevelThreeService;
import at.meroff.itproject.repository.search.CollisionLevelThreeSearchRepository;
import at.meroff.itproject.service.dto.CollisionLevelThreeDTO;
import at.meroff.itproject.service.mapper.CollisionLevelThreeMapper;
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
 * Test class for the CollisionLevelThreeResource REST controller.
 *
 * @see CollisionLevelThreeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class CollisionLevelThreeResourceIntTest {

    private static final Integer DEFAULT_EXAM_COLLISION = 1;
    private static final Integer UPDATED_EXAM_COLLISION = 2;

    @Autowired
    private CollisionLevelThreeRepository collisionLevelThreeRepository;

    @Autowired
    private CollisionLevelThreeMapper collisionLevelThreeMapper;

    @Autowired
    private CollisionLevelThreeService collisionLevelThreeService;

    @Autowired
    private CollisionLevelThreeSearchRepository collisionLevelThreeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollisionLevelThreeMockMvc;

    private CollisionLevelThree collisionLevelThree;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CollisionLevelThreeResource collisionLevelThreeResource = new CollisionLevelThreeResource(collisionLevelThreeService);
        this.restCollisionLevelThreeMockMvc = MockMvcBuilders.standaloneSetup(collisionLevelThreeResource)
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
    public static CollisionLevelThree createEntity(EntityManager em) {
        CollisionLevelThree collisionLevelThree = new CollisionLevelThree()
            .examCollision(DEFAULT_EXAM_COLLISION);
        return collisionLevelThree;
    }

    @Before
    public void initTest() {
        collisionLevelThreeSearchRepository.deleteAll();
        collisionLevelThree = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollisionLevelThree() throws Exception {
        int databaseSizeBeforeCreate = collisionLevelThreeRepository.findAll().size();

        // Create the CollisionLevelThree
        CollisionLevelThreeDTO collisionLevelThreeDTO = collisionLevelThreeMapper.toDto(collisionLevelThree);
        restCollisionLevelThreeMockMvc.perform(post("/api/collision-level-threes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelThreeDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionLevelThree in the database
        List<CollisionLevelThree> collisionLevelThreeList = collisionLevelThreeRepository.findAll();
        assertThat(collisionLevelThreeList).hasSize(databaseSizeBeforeCreate + 1);
        CollisionLevelThree testCollisionLevelThree = collisionLevelThreeList.get(collisionLevelThreeList.size() - 1);
        assertThat(testCollisionLevelThree.getExamCollision()).isEqualTo(DEFAULT_EXAM_COLLISION);

        // Validate the CollisionLevelThree in Elasticsearch
        CollisionLevelThree collisionLevelThreeEs = collisionLevelThreeSearchRepository.findOne(testCollisionLevelThree.getId());
        assertThat(collisionLevelThreeEs).isEqualToComparingFieldByField(testCollisionLevelThree);
    }

    @Test
    @Transactional
    public void createCollisionLevelThreeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collisionLevelThreeRepository.findAll().size();

        // Create the CollisionLevelThree with an existing ID
        collisionLevelThree.setId(1L);
        CollisionLevelThreeDTO collisionLevelThreeDTO = collisionLevelThreeMapper.toDto(collisionLevelThree);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollisionLevelThreeMockMvc.perform(post("/api/collision-level-threes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelThreeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CollisionLevelThree> collisionLevelThreeList = collisionLevelThreeRepository.findAll();
        assertThat(collisionLevelThreeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCollisionLevelThrees() throws Exception {
        // Initialize the database
        collisionLevelThreeRepository.saveAndFlush(collisionLevelThree);

        // Get all the collisionLevelThreeList
        restCollisionLevelThreeMockMvc.perform(get("/api/collision-level-threes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionLevelThree.getId().intValue())))
            .andExpect(jsonPath("$.[*].examCollision").value(hasItem(DEFAULT_EXAM_COLLISION)));
    }

    @Test
    @Transactional
    public void getCollisionLevelThree() throws Exception {
        // Initialize the database
        collisionLevelThreeRepository.saveAndFlush(collisionLevelThree);

        // Get the collisionLevelThree
        restCollisionLevelThreeMockMvc.perform(get("/api/collision-level-threes/{id}", collisionLevelThree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collisionLevelThree.getId().intValue()))
            .andExpect(jsonPath("$.examCollision").value(DEFAULT_EXAM_COLLISION));
    }

    @Test
    @Transactional
    public void getNonExistingCollisionLevelThree() throws Exception {
        // Get the collisionLevelThree
        restCollisionLevelThreeMockMvc.perform(get("/api/collision-level-threes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollisionLevelThree() throws Exception {
        // Initialize the database
        collisionLevelThreeRepository.saveAndFlush(collisionLevelThree);
        collisionLevelThreeSearchRepository.save(collisionLevelThree);
        int databaseSizeBeforeUpdate = collisionLevelThreeRepository.findAll().size();

        // Update the collisionLevelThree
        CollisionLevelThree updatedCollisionLevelThree = collisionLevelThreeRepository.findOne(collisionLevelThree.getId());
        updatedCollisionLevelThree
            .examCollision(UPDATED_EXAM_COLLISION);
        CollisionLevelThreeDTO collisionLevelThreeDTO = collisionLevelThreeMapper.toDto(updatedCollisionLevelThree);

        restCollisionLevelThreeMockMvc.perform(put("/api/collision-level-threes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelThreeDTO)))
            .andExpect(status().isOk());

        // Validate the CollisionLevelThree in the database
        List<CollisionLevelThree> collisionLevelThreeList = collisionLevelThreeRepository.findAll();
        assertThat(collisionLevelThreeList).hasSize(databaseSizeBeforeUpdate);
        CollisionLevelThree testCollisionLevelThree = collisionLevelThreeList.get(collisionLevelThreeList.size() - 1);
        assertThat(testCollisionLevelThree.getExamCollision()).isEqualTo(UPDATED_EXAM_COLLISION);

        // Validate the CollisionLevelThree in Elasticsearch
        CollisionLevelThree collisionLevelThreeEs = collisionLevelThreeSearchRepository.findOne(testCollisionLevelThree.getId());
        assertThat(collisionLevelThreeEs).isEqualToComparingFieldByField(testCollisionLevelThree);
    }

    @Test
    @Transactional
    public void updateNonExistingCollisionLevelThree() throws Exception {
        int databaseSizeBeforeUpdate = collisionLevelThreeRepository.findAll().size();

        // Create the CollisionLevelThree
        CollisionLevelThreeDTO collisionLevelThreeDTO = collisionLevelThreeMapper.toDto(collisionLevelThree);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollisionLevelThreeMockMvc.perform(put("/api/collision-level-threes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelThreeDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionLevelThree in the database
        List<CollisionLevelThree> collisionLevelThreeList = collisionLevelThreeRepository.findAll();
        assertThat(collisionLevelThreeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollisionLevelThree() throws Exception {
        // Initialize the database
        collisionLevelThreeRepository.saveAndFlush(collisionLevelThree);
        collisionLevelThreeSearchRepository.save(collisionLevelThree);
        int databaseSizeBeforeDelete = collisionLevelThreeRepository.findAll().size();

        // Get the collisionLevelThree
        restCollisionLevelThreeMockMvc.perform(delete("/api/collision-level-threes/{id}", collisionLevelThree.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean collisionLevelThreeExistsInEs = collisionLevelThreeSearchRepository.exists(collisionLevelThree.getId());
        assertThat(collisionLevelThreeExistsInEs).isFalse();

        // Validate the database is empty
        List<CollisionLevelThree> collisionLevelThreeList = collisionLevelThreeRepository.findAll();
        assertThat(collisionLevelThreeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCollisionLevelThree() throws Exception {
        // Initialize the database
        collisionLevelThreeRepository.saveAndFlush(collisionLevelThree);
        collisionLevelThreeSearchRepository.save(collisionLevelThree);

        // Search the collisionLevelThree
        restCollisionLevelThreeMockMvc.perform(get("/api/_search/collision-level-threes?query=id:" + collisionLevelThree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionLevelThree.getId().intValue())))
            .andExpect(jsonPath("$.[*].examCollision").value(hasItem(DEFAULT_EXAM_COLLISION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionLevelThree.class);
        CollisionLevelThree collisionLevelThree1 = new CollisionLevelThree();
        collisionLevelThree1.setId(1L);
        CollisionLevelThree collisionLevelThree2 = new CollisionLevelThree();
        collisionLevelThree2.setId(collisionLevelThree1.getId());
        assertThat(collisionLevelThree1).isEqualTo(collisionLevelThree2);
        collisionLevelThree2.setId(2L);
        assertThat(collisionLevelThree1).isNotEqualTo(collisionLevelThree2);
        collisionLevelThree1.setId(null);
        assertThat(collisionLevelThree1).isNotEqualTo(collisionLevelThree2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionLevelThreeDTO.class);
        CollisionLevelThreeDTO collisionLevelThreeDTO1 = new CollisionLevelThreeDTO();
        collisionLevelThreeDTO1.setId(1L);
        CollisionLevelThreeDTO collisionLevelThreeDTO2 = new CollisionLevelThreeDTO();
        assertThat(collisionLevelThreeDTO1).isNotEqualTo(collisionLevelThreeDTO2);
        collisionLevelThreeDTO2.setId(collisionLevelThreeDTO1.getId());
        assertThat(collisionLevelThreeDTO1).isEqualTo(collisionLevelThreeDTO2);
        collisionLevelThreeDTO2.setId(2L);
        assertThat(collisionLevelThreeDTO1).isNotEqualTo(collisionLevelThreeDTO2);
        collisionLevelThreeDTO1.setId(null);
        assertThat(collisionLevelThreeDTO1).isNotEqualTo(collisionLevelThreeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(collisionLevelThreeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(collisionLevelThreeMapper.fromId(null)).isNull();
    }
}
