package at.meroff.itproject.web.rest;

import at.meroff.itproject.CeappApp;

import at.meroff.itproject.domain.CollisionSummaryCS;
import at.meroff.itproject.repository.CollisionSummaryCSRepository;
import at.meroff.itproject.service.CollisionSummaryCSService;
import at.meroff.itproject.service.dto.CollisionSummaryCSDTO;
import at.meroff.itproject.service.mapper.CollisionSummaryCSMapper;
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
 * Test class for the CollisionSummaryCSResource REST controller.
 *
 * @see CollisionSummaryCSResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeappApp.class)
public class CollisionSummaryCSResourceIntTest {

    private static final Integer DEFAULT_INSTITUTE_COLLISION = 1;
    private static final Integer UPDATED_INSTITUTE_COLLISION = 2;

    @Autowired
    private CollisionSummaryCSRepository collisionSummaryCSRepository;

    @Autowired
    private CollisionSummaryCSMapper collisionSummaryCSMapper;

    @Autowired
    private CollisionSummaryCSService collisionSummaryCSService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollisionSummaryCSMockMvc;

    private CollisionSummaryCS collisionSummaryCS;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CollisionSummaryCSResource collisionSummaryCSResource = new CollisionSummaryCSResource(collisionSummaryCSService);
        this.restCollisionSummaryCSMockMvc = MockMvcBuilders.standaloneSetup(collisionSummaryCSResource)
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
    public static CollisionSummaryCS createEntity(EntityManager em) {
        CollisionSummaryCS collisionSummaryCS = new CollisionSummaryCS()
            .instituteCollision(DEFAULT_INSTITUTE_COLLISION);
        return collisionSummaryCS;
    }

    @Before
    public void initTest() {
        collisionSummaryCS = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollisionSummaryCS() throws Exception {
        int databaseSizeBeforeCreate = collisionSummaryCSRepository.findAll().size();

        // Create the CollisionSummaryCS
        CollisionSummaryCSDTO collisionSummaryCSDTO = collisionSummaryCSMapper.toDto(collisionSummaryCS);
        restCollisionSummaryCSMockMvc.perform(post("/api/collision-summary-cs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionSummaryCSDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionSummaryCS in the database
        List<CollisionSummaryCS> collisionSummaryCSList = collisionSummaryCSRepository.findAll();
        assertThat(collisionSummaryCSList).hasSize(databaseSizeBeforeCreate + 1);
        CollisionSummaryCS testCollisionSummaryCS = collisionSummaryCSList.get(collisionSummaryCSList.size() - 1);
        assertThat(testCollisionSummaryCS.getInstituteCollision()).isEqualTo(DEFAULT_INSTITUTE_COLLISION);
    }

    @Test
    @Transactional
    public void createCollisionSummaryCSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collisionSummaryCSRepository.findAll().size();

        // Create the CollisionSummaryCS with an existing ID
        collisionSummaryCS.setId(1L);
        CollisionSummaryCSDTO collisionSummaryCSDTO = collisionSummaryCSMapper.toDto(collisionSummaryCS);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollisionSummaryCSMockMvc.perform(post("/api/collision-summary-cs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionSummaryCSDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CollisionSummaryCS> collisionSummaryCSList = collisionSummaryCSRepository.findAll();
        assertThat(collisionSummaryCSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCollisionSummaryCS() throws Exception {
        // Initialize the database
        collisionSummaryCSRepository.saveAndFlush(collisionSummaryCS);

        // Get all the collisionSummaryCSList
        restCollisionSummaryCSMockMvc.perform(get("/api/collision-summary-cs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collisionSummaryCS.getId().intValue())))
            .andExpect(jsonPath("$.[*].instituteCollision").value(hasItem(DEFAULT_INSTITUTE_COLLISION)));
    }

    @Test
    @Transactional
    public void getCollisionSummaryCS() throws Exception {
        // Initialize the database
        collisionSummaryCSRepository.saveAndFlush(collisionSummaryCS);

        // Get the collisionSummaryCS
        restCollisionSummaryCSMockMvc.perform(get("/api/collision-summary-cs/{id}", collisionSummaryCS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collisionSummaryCS.getId().intValue()))
            .andExpect(jsonPath("$.instituteCollision").value(DEFAULT_INSTITUTE_COLLISION));
    }

    @Test
    @Transactional
    public void getNonExistingCollisionSummaryCS() throws Exception {
        // Get the collisionSummaryCS
        restCollisionSummaryCSMockMvc.perform(get("/api/collision-summary-cs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollisionSummaryCS() throws Exception {
        // Initialize the database
        collisionSummaryCSRepository.saveAndFlush(collisionSummaryCS);
        int databaseSizeBeforeUpdate = collisionSummaryCSRepository.findAll().size();

        // Update the collisionSummaryCS
        CollisionSummaryCS updatedCollisionSummaryCS = collisionSummaryCSRepository.findOne(collisionSummaryCS.getId());
        updatedCollisionSummaryCS
            .instituteCollision(UPDATED_INSTITUTE_COLLISION);
        CollisionSummaryCSDTO collisionSummaryCSDTO = collisionSummaryCSMapper.toDto(updatedCollisionSummaryCS);

        restCollisionSummaryCSMockMvc.perform(put("/api/collision-summary-cs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionSummaryCSDTO)))
            .andExpect(status().isOk());

        // Validate the CollisionSummaryCS in the database
        List<CollisionSummaryCS> collisionSummaryCSList = collisionSummaryCSRepository.findAll();
        assertThat(collisionSummaryCSList).hasSize(databaseSizeBeforeUpdate);
        CollisionSummaryCS testCollisionSummaryCS = collisionSummaryCSList.get(collisionSummaryCSList.size() - 1);
        assertThat(testCollisionSummaryCS.getInstituteCollision()).isEqualTo(UPDATED_INSTITUTE_COLLISION);
    }

    @Test
    @Transactional
    public void updateNonExistingCollisionSummaryCS() throws Exception {
        int databaseSizeBeforeUpdate = collisionSummaryCSRepository.findAll().size();

        // Create the CollisionSummaryCS
        CollisionSummaryCSDTO collisionSummaryCSDTO = collisionSummaryCSMapper.toDto(collisionSummaryCS);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollisionSummaryCSMockMvc.perform(put("/api/collision-summary-cs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collisionSummaryCSDTO)))
            .andExpect(status().isCreated());

        // Validate the CollisionSummaryCS in the database
        List<CollisionSummaryCS> collisionSummaryCSList = collisionSummaryCSRepository.findAll();
        assertThat(collisionSummaryCSList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollisionSummaryCS() throws Exception {
        // Initialize the database
        collisionSummaryCSRepository.saveAndFlush(collisionSummaryCS);
        int databaseSizeBeforeDelete = collisionSummaryCSRepository.findAll().size();

        // Get the collisionSummaryCS
        restCollisionSummaryCSMockMvc.perform(delete("/api/collision-summary-cs/{id}", collisionSummaryCS.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CollisionSummaryCS> collisionSummaryCSList = collisionSummaryCSRepository.findAll();
        assertThat(collisionSummaryCSList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionSummaryCS.class);
        CollisionSummaryCS collisionSummaryCS1 = new CollisionSummaryCS();
        collisionSummaryCS1.setId(1L);
        CollisionSummaryCS collisionSummaryCS2 = new CollisionSummaryCS();
        collisionSummaryCS2.setId(collisionSummaryCS1.getId());
        assertThat(collisionSummaryCS1).isEqualTo(collisionSummaryCS2);
        collisionSummaryCS2.setId(2L);
        assertThat(collisionSummaryCS1).isNotEqualTo(collisionSummaryCS2);
        collisionSummaryCS1.setId(null);
        assertThat(collisionSummaryCS1).isNotEqualTo(collisionSummaryCS2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollisionSummaryCSDTO.class);
        CollisionSummaryCSDTO collisionSummaryCSDTO1 = new CollisionSummaryCSDTO();
        collisionSummaryCSDTO1.setId(1L);
        CollisionSummaryCSDTO collisionSummaryCSDTO2 = new CollisionSummaryCSDTO();
        assertThat(collisionSummaryCSDTO1).isNotEqualTo(collisionSummaryCSDTO2);
        collisionSummaryCSDTO2.setId(collisionSummaryCSDTO1.getId());
        assertThat(collisionSummaryCSDTO1).isEqualTo(collisionSummaryCSDTO2);
        collisionSummaryCSDTO2.setId(2L);
        assertThat(collisionSummaryCSDTO1).isNotEqualTo(collisionSummaryCSDTO2);
        collisionSummaryCSDTO1.setId(null);
        assertThat(collisionSummaryCSDTO1).isNotEqualTo(collisionSummaryCSDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(collisionSummaryCSMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(collisionSummaryCSMapper.fromId(null)).isNull();
    }
}
