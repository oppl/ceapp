package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.CollisionSummaryLva;
import at.meroff.itproject.repository.CollisionSummaryLvaRepository;
import at.meroff.itproject.service.CollisionSummaryLvaService;
import at.meroff.itproject.repository.search.CollisionSummaryLvaSearchRepository;
import at.meroff.itproject.service.dto.CollisionSummaryLvaDTO;
import at.meroff.itproject.service.mapper.CollisionSummaryLvaMapper;
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
 * Test class for the CollisionSummaryLvaResource REST controller.
 *
 * @see CollisionSummaryLvaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class CollisionSummaryLvaResourceIntTest {

    private static final Integer DEFAULT_INSTITUTE_COLLISION = 1;
    private static final Integer UPDATED_INSTITUTE_COLLISION = 2;

    @Autowired
    private CollisionSummaryLvaRepository collisionSummaryLvaRepository;

    @Autowired
    private CollisionSummaryLvaMapper collisionSummaryLvaMapper;

    @Autowired
    private CollisionSummaryLvaService collisionSummaryLvaService;

    @Autowired
    private CollisionSummaryLvaSearchRepository collisionSummaryLvaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollisionSummaryLvaMockMvc;

    private CollisionSummaryLva collisionSummaryLva;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CollisionSummaryLvaResource collisionSummaryLvaResource = new CollisionSummaryLvaResource(collisionSummaryLvaService);
        this.restCollisionSummaryLvaMockMvc = MockMvcBuilders.standaloneSetup(collisionSummaryLvaResource)
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
    public static CollisionSummaryLva createEntity(EntityManager em) {
        CollisionSummaryLva collisionSummaryLva = new CollisionSummaryLva()
            .instituteCollision(DEFAULT_INSTITUTE_COLLISION);
        return collisionSummaryLva;
    }

    @Before
    public void initTest() {
        collisionSummaryLvaSearchRepository.deleteAll();
        collisionSummaryLva = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollisionSummaryLva() throws Exception {
        int databaseSizeBeforeCreate = collisionSummaryLvaRepository.findAll().size();

        // Create the CollisionSummaryLva
        CollisionSummaryLvaDTO collisionSummaryLvaDTO = collisionSummaryLvaMapper.toDto(collisionSummaryLva);
        restCollisionSummaryLvaMockMvc.perform(post("/api/collision-summary-lvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionSummaryLvaDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionSummaryLva in the database
        List<CollisionSummaryLva> collisionSummaryLvaList = collisionSummaryLvaRepository.findAll();
        assertThat(collisionSummaryLvaList).hasSize(databaseSizeBeforeCreate + 1);
        CollisionSummaryLva testCollisionSummaryLva = collisionSummaryLvaList.get(collisionSummaryLvaList.size() - 1);
        assertThat(testCollisionSummaryLva.getInstituteCollision()).isEqualTo(DEFAULT_INSTITUTE_COLLISION);

        // Validate the CollisionSummaryLva in Elasticsearch
        CollisionSummaryLva collisionSummaryLvaEs = collisionSummaryLvaSearchRepository.findOne(testCollisionSummaryLva.getId());
        assertThat(collisionSummaryLvaEs).isEqualToComparingFieldByField(testCollisionSummaryLva);
    }

    @Test
    @Transactional
    public void createCollisionSummaryLvaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collisionSummaryLvaRepository.findAll().size();

        // Create the CollisionSummaryLva with an existing ID
        collisionSummaryLva.setId(1L);
        CollisionSummaryLvaDTO collisionSummaryLvaDTO = collisionSummaryLvaMapper.toDto(collisionSummaryLva);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollisionSummaryLvaMockMvc.perform(post("/api/collision-summary-lvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionSummaryLvaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CollisionSummaryLva> collisionSummaryLvaList = collisionSummaryLvaRepository.findAll();
        assertThat(collisionSummaryLvaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCollisionSummaryLvas() throws Exception {
        // Initialize the database
        collisionSummaryLvaRepository.saveAndFlush(collisionSummaryLva);

        // Get all the collisionSummaryLvaList
        restCollisionSummaryLvaMockMvc.perform(get("/api/collision-summary-lvas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionSummaryLva.getId().intValue())))
            .andExpect(jsonPath("$.[*].instituteCollision").value(hasItem(DEFAULT_INSTITUTE_COLLISION)));
    }

    @Test
    @Transactional
    public void getCollisionSummaryLva() throws Exception {
        // Initialize the database
        collisionSummaryLvaRepository.saveAndFlush(collisionSummaryLva);

        // Get the collisionSummaryLva
        restCollisionSummaryLvaMockMvc.perform(get("/api/collision-summary-lvas/{id}", collisionSummaryLva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collisionSummaryLva.getId().intValue()))
            .andExpect(jsonPath("$.instituteCollision").value(DEFAULT_INSTITUTE_COLLISION));
    }

    @Test
    @Transactional
    public void getNonExistingCollisionSummaryLva() throws Exception {
        // Get the collisionSummaryLva
        restCollisionSummaryLvaMockMvc.perform(get("/api/collision-summary-lvas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollisionSummaryLva() throws Exception {
        // Initialize the database
        collisionSummaryLvaRepository.saveAndFlush(collisionSummaryLva);
        collisionSummaryLvaSearchRepository.save(collisionSummaryLva);
        int databaseSizeBeforeUpdate = collisionSummaryLvaRepository.findAll().size();

        // Update the collisionSummaryLva
        CollisionSummaryLva updatedCollisionSummaryLva = collisionSummaryLvaRepository.findOne(collisionSummaryLva.getId());
        updatedCollisionSummaryLva
            .instituteCollision(UPDATED_INSTITUTE_COLLISION);
        CollisionSummaryLvaDTO collisionSummaryLvaDTO = collisionSummaryLvaMapper.toDto(updatedCollisionSummaryLva);

        restCollisionSummaryLvaMockMvc.perform(put("/api/collision-summary-lvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionSummaryLvaDTO)))
            .andExpect(status().isOk());

        // Validate the CollisionSummaryLva in the database
        List<CollisionSummaryLva> collisionSummaryLvaList = collisionSummaryLvaRepository.findAll();
        assertThat(collisionSummaryLvaList).hasSize(databaseSizeBeforeUpdate);
        CollisionSummaryLva testCollisionSummaryLva = collisionSummaryLvaList.get(collisionSummaryLvaList.size() - 1);
        assertThat(testCollisionSummaryLva.getInstituteCollision()).isEqualTo(UPDATED_INSTITUTE_COLLISION);

        // Validate the CollisionSummaryLva in Elasticsearch
        CollisionSummaryLva collisionSummaryLvaEs = collisionSummaryLvaSearchRepository.findOne(testCollisionSummaryLva.getId());
        assertThat(collisionSummaryLvaEs).isEqualToComparingFieldByField(testCollisionSummaryLva);
    }

    @Test
    @Transactional
    public void updateNonExistingCollisionSummaryLva() throws Exception {
        int databaseSizeBeforeUpdate = collisionSummaryLvaRepository.findAll().size();

        // Create the CollisionSummaryLva
        CollisionSummaryLvaDTO collisionSummaryLvaDTO = collisionSummaryLvaMapper.toDto(collisionSummaryLva);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollisionSummaryLvaMockMvc.perform(put("/api/collision-summary-lvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionSummaryLvaDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionSummaryLva in the database
        List<CollisionSummaryLva> collisionSummaryLvaList = collisionSummaryLvaRepository.findAll();
        assertThat(collisionSummaryLvaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollisionSummaryLva() throws Exception {
        // Initialize the database
        collisionSummaryLvaRepository.saveAndFlush(collisionSummaryLva);
        collisionSummaryLvaSearchRepository.save(collisionSummaryLva);
        int databaseSizeBeforeDelete = collisionSummaryLvaRepository.findAll().size();

        // Get the collisionSummaryLva
        restCollisionSummaryLvaMockMvc.perform(delete("/api/collision-summary-lvas/{id}", collisionSummaryLva.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean collisionSummaryLvaExistsInEs = collisionSummaryLvaSearchRepository.exists(collisionSummaryLva.getId());
        assertThat(collisionSummaryLvaExistsInEs).isFalse();

        // Validate the database is empty
        List<CollisionSummaryLva> collisionSummaryLvaList = collisionSummaryLvaRepository.findAll();
        assertThat(collisionSummaryLvaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCollisionSummaryLva() throws Exception {
        // Initialize the database
        collisionSummaryLvaRepository.saveAndFlush(collisionSummaryLva);
        collisionSummaryLvaSearchRepository.save(collisionSummaryLva);

        // Search the collisionSummaryLva
        restCollisionSummaryLvaMockMvc.perform(get("/api/_search/collision-summary-lvas?query=id:" + collisionSummaryLva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionSummaryLva.getId().intValue())))
            .andExpect(jsonPath("$.[*].instituteCollision").value(hasItem(DEFAULT_INSTITUTE_COLLISION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionSummaryLva.class);
        CollisionSummaryLva collisionSummaryLva1 = new CollisionSummaryLva();
        collisionSummaryLva1.setId(1L);
        CollisionSummaryLva collisionSummaryLva2 = new CollisionSummaryLva();
        collisionSummaryLva2.setId(collisionSummaryLva1.getId());
        assertThat(collisionSummaryLva1).isEqualTo(collisionSummaryLva2);
        collisionSummaryLva2.setId(2L);
        assertThat(collisionSummaryLva1).isNotEqualTo(collisionSummaryLva2);
        collisionSummaryLva1.setId(null);
        assertThat(collisionSummaryLva1).isNotEqualTo(collisionSummaryLva2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionSummaryLvaDTO.class);
        CollisionSummaryLvaDTO collisionSummaryLvaDTO1 = new CollisionSummaryLvaDTO();
        collisionSummaryLvaDTO1.setId(1L);
        CollisionSummaryLvaDTO collisionSummaryLvaDTO2 = new CollisionSummaryLvaDTO();
        assertThat(collisionSummaryLvaDTO1).isNotEqualTo(collisionSummaryLvaDTO2);
        collisionSummaryLvaDTO2.setId(collisionSummaryLvaDTO1.getId());
        assertThat(collisionSummaryLvaDTO1).isEqualTo(collisionSummaryLvaDTO2);
        collisionSummaryLvaDTO2.setId(2L);
        assertThat(collisionSummaryLvaDTO1).isNotEqualTo(collisionSummaryLvaDTO2);
        collisionSummaryLvaDTO1.setId(null);
        assertThat(collisionSummaryLvaDTO1).isNotEqualTo(collisionSummaryLvaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(collisionSummaryLvaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(collisionSummaryLvaMapper.fromId(null)).isNull();
    }
}
