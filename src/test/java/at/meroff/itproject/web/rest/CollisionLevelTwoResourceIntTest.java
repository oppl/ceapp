package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.CollisionLevelTwo;
import at.meroff.itproject.repository.CollisionLevelTwoRepository;
import at.meroff.itproject.service.CollisionLevelTwoService;
import at.meroff.itproject.repository.search.CollisionLevelTwoSearchRepository;
import at.meroff.itproject.service.dto.CollisionLevelTwoDTO;
import at.meroff.itproject.service.mapper.CollisionLevelTwoMapper;
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
 * Test class for the CollisionLevelTwoResource REST controller.
 *
 * @see CollisionLevelTwoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class CollisionLevelTwoResourceIntTest {

    private static final Integer DEFAULT_EXAM_COLLISION = 1;
    private static final Integer UPDATED_EXAM_COLLISION = 2;

    @Autowired
    private CollisionLevelTwoRepository collisionLevelTwoRepository;

    @Autowired
    private CollisionLevelTwoMapper collisionLevelTwoMapper;

    @Autowired
    private CollisionLevelTwoService collisionLevelTwoService;

    @Autowired
    private CollisionLevelTwoSearchRepository collisionLevelTwoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollisionLevelTwoMockMvc;

    private CollisionLevelTwo collisionLevelTwo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CollisionLevelTwoResource collisionLevelTwoResource = new CollisionLevelTwoResource(collisionLevelTwoService);
        this.restCollisionLevelTwoMockMvc = MockMvcBuilders.standaloneSetup(collisionLevelTwoResource)
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
    public static CollisionLevelTwo createEntity(EntityManager em) {
        CollisionLevelTwo collisionLevelTwo = new CollisionLevelTwo()
            .examCollision(DEFAULT_EXAM_COLLISION);
        return collisionLevelTwo;
    }

    @Before
    public void initTest() {
        collisionLevelTwoSearchRepository.deleteAll();
        collisionLevelTwo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollisionLevelTwo() throws Exception {
        int databaseSizeBeforeCreate = collisionLevelTwoRepository.findAll().size();

        // Create the CollisionLevelTwo
        CollisionLevelTwoDTO collisionLevelTwoDTO = collisionLevelTwoMapper.toDto(collisionLevelTwo);
        restCollisionLevelTwoMockMvc.perform(post("/api/collision-level-twos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelTwoDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionLevelTwo in the database
        List<CollisionLevelTwo> collisionLevelTwoList = collisionLevelTwoRepository.findAll();
        assertThat(collisionLevelTwoList).hasSize(databaseSizeBeforeCreate + 1);
        CollisionLevelTwo testCollisionLevelTwo = collisionLevelTwoList.get(collisionLevelTwoList.size() - 1);
        assertThat(testCollisionLevelTwo.getExamCollision()).isEqualTo(DEFAULT_EXAM_COLLISION);

        // Validate the CollisionLevelTwo in Elasticsearch
        CollisionLevelTwo collisionLevelTwoEs = collisionLevelTwoSearchRepository.findOne(testCollisionLevelTwo.getId());
        assertThat(collisionLevelTwoEs).isEqualToComparingFieldByField(testCollisionLevelTwo);
    }

    @Test
    @Transactional
    public void createCollisionLevelTwoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collisionLevelTwoRepository.findAll().size();

        // Create the CollisionLevelTwo with an existing ID
        collisionLevelTwo.setId(1L);
        CollisionLevelTwoDTO collisionLevelTwoDTO = collisionLevelTwoMapper.toDto(collisionLevelTwo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollisionLevelTwoMockMvc.perform(post("/api/collision-level-twos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelTwoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CollisionLevelTwo> collisionLevelTwoList = collisionLevelTwoRepository.findAll();
        assertThat(collisionLevelTwoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCollisionLevelTwos() throws Exception {
        // Initialize the database
        collisionLevelTwoRepository.saveAndFlush(collisionLevelTwo);

        // Get all the collisionLevelTwoList
        restCollisionLevelTwoMockMvc.perform(get("/api/collision-level-twos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionLevelTwo.getId().intValue())))
            .andExpect(jsonPath("$.[*].examCollision").value(hasItem(DEFAULT_EXAM_COLLISION)));
    }

    @Test
    @Transactional
    public void getCollisionLevelTwo() throws Exception {
        // Initialize the database
        collisionLevelTwoRepository.saveAndFlush(collisionLevelTwo);

        // Get the collisionLevelTwo
        restCollisionLevelTwoMockMvc.perform(get("/api/collision-level-twos/{id}", collisionLevelTwo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collisionLevelTwo.getId().intValue()))
            .andExpect(jsonPath("$.examCollision").value(DEFAULT_EXAM_COLLISION));
    }

    @Test
    @Transactional
    public void getNonExistingCollisionLevelTwo() throws Exception {
        // Get the collisionLevelTwo
        restCollisionLevelTwoMockMvc.perform(get("/api/collision-level-twos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollisionLevelTwo() throws Exception {
        // Initialize the database
        collisionLevelTwoRepository.saveAndFlush(collisionLevelTwo);
        collisionLevelTwoSearchRepository.save(collisionLevelTwo);
        int databaseSizeBeforeUpdate = collisionLevelTwoRepository.findAll().size();

        // Update the collisionLevelTwo
        CollisionLevelTwo updatedCollisionLevelTwo = collisionLevelTwoRepository.findOne(collisionLevelTwo.getId());
        updatedCollisionLevelTwo
            .examCollision(UPDATED_EXAM_COLLISION);
        CollisionLevelTwoDTO collisionLevelTwoDTO = collisionLevelTwoMapper.toDto(updatedCollisionLevelTwo);

        restCollisionLevelTwoMockMvc.perform(put("/api/collision-level-twos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelTwoDTO)))
            .andExpect(status().isOk());

        // Validate the CollisionLevelTwo in the database
        List<CollisionLevelTwo> collisionLevelTwoList = collisionLevelTwoRepository.findAll();
        assertThat(collisionLevelTwoList).hasSize(databaseSizeBeforeUpdate);
        CollisionLevelTwo testCollisionLevelTwo = collisionLevelTwoList.get(collisionLevelTwoList.size() - 1);
        assertThat(testCollisionLevelTwo.getExamCollision()).isEqualTo(UPDATED_EXAM_COLLISION);

        // Validate the CollisionLevelTwo in Elasticsearch
        CollisionLevelTwo collisionLevelTwoEs = collisionLevelTwoSearchRepository.findOne(testCollisionLevelTwo.getId());
        assertThat(collisionLevelTwoEs).isEqualToComparingFieldByField(testCollisionLevelTwo);
    }

    @Test
    @Transactional
    public void updateNonExistingCollisionLevelTwo() throws Exception {
        int databaseSizeBeforeUpdate = collisionLevelTwoRepository.findAll().size();

        // Create the CollisionLevelTwo
        CollisionLevelTwoDTO collisionLevelTwoDTO = collisionLevelTwoMapper.toDto(collisionLevelTwo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollisionLevelTwoMockMvc.perform(put("/api/collision-level-twos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionLevelTwoDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionLevelTwo in the database
        List<CollisionLevelTwo> collisionLevelTwoList = collisionLevelTwoRepository.findAll();
        assertThat(collisionLevelTwoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollisionLevelTwo() throws Exception {
        // Initialize the database
        collisionLevelTwoRepository.saveAndFlush(collisionLevelTwo);
        collisionLevelTwoSearchRepository.save(collisionLevelTwo);
        int databaseSizeBeforeDelete = collisionLevelTwoRepository.findAll().size();

        // Get the collisionLevelTwo
        restCollisionLevelTwoMockMvc.perform(delete("/api/collision-level-twos/{id}", collisionLevelTwo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean collisionLevelTwoExistsInEs = collisionLevelTwoSearchRepository.exists(collisionLevelTwo.getId());
        assertThat(collisionLevelTwoExistsInEs).isFalse();

        // Validate the database is empty
        List<CollisionLevelTwo> collisionLevelTwoList = collisionLevelTwoRepository.findAll();
        assertThat(collisionLevelTwoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCollisionLevelTwo() throws Exception {
        // Initialize the database
        collisionLevelTwoRepository.saveAndFlush(collisionLevelTwo);
        collisionLevelTwoSearchRepository.save(collisionLevelTwo);

        // Search the collisionLevelTwo
        restCollisionLevelTwoMockMvc.perform(get("/api/_search/collision-level-twos?query=id:" + collisionLevelTwo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionLevelTwo.getId().intValue())))
            .andExpect(jsonPath("$.[*].examCollision").value(hasItem(DEFAULT_EXAM_COLLISION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionLevelTwo.class);
        CollisionLevelTwo collisionLevelTwo1 = new CollisionLevelTwo();
        collisionLevelTwo1.setId(1L);
        CollisionLevelTwo collisionLevelTwo2 = new CollisionLevelTwo();
        collisionLevelTwo2.setId(collisionLevelTwo1.getId());
        assertThat(collisionLevelTwo1).isEqualTo(collisionLevelTwo2);
        collisionLevelTwo2.setId(2L);
        assertThat(collisionLevelTwo1).isNotEqualTo(collisionLevelTwo2);
        collisionLevelTwo1.setId(null);
        assertThat(collisionLevelTwo1).isNotEqualTo(collisionLevelTwo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionLevelTwoDTO.class);
        CollisionLevelTwoDTO collisionLevelTwoDTO1 = new CollisionLevelTwoDTO();
        collisionLevelTwoDTO1.setId(1L);
        CollisionLevelTwoDTO collisionLevelTwoDTO2 = new CollisionLevelTwoDTO();
        assertThat(collisionLevelTwoDTO1).isNotEqualTo(collisionLevelTwoDTO2);
        collisionLevelTwoDTO2.setId(collisionLevelTwoDTO1.getId());
        assertThat(collisionLevelTwoDTO1).isEqualTo(collisionLevelTwoDTO2);
        collisionLevelTwoDTO2.setId(2L);
        assertThat(collisionLevelTwoDTO1).isNotEqualTo(collisionLevelTwoDTO2);
        collisionLevelTwoDTO1.setId(null);
        assertThat(collisionLevelTwoDTO1).isNotEqualTo(collisionLevelTwoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(collisionLevelTwoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(collisionLevelTwoMapper.fromId(null)).isNull();
    }
}
