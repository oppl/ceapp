package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.CollisionLevelThreeService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.CollisionLevelThreeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CollisionLevelThree.
 */
@RestController
@RequestMapping("/api")
public class CollisionLevelThreeResource {

    private final Logger log = LoggerFactory.getLogger(CollisionLevelThreeResource.class);

    private static final String ENTITY_NAME = "collisionLevelThree";

    private final CollisionLevelThreeService collisionLevelThreeService;

    public CollisionLevelThreeResource(CollisionLevelThreeService collisionLevelThreeService) {
        this.collisionLevelThreeService = collisionLevelThreeService;
    }

    /**
     * POST  /collision-level-threes : Create a new collisionLevelThree.
     *
     * @param collisionLevelThreeDTO the collisionLevelThreeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collisionLevelThreeDTO, or with status 400 (Bad Request) if the collisionLevelThree has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collision-level-threes")
    @Timed
    public ResponseEntity<CollisionLevelThreeDTO> createCollisionLevelThree(@RequestBody CollisionLevelThreeDTO collisionLevelThreeDTO) throws URISyntaxException {
        log.debug("REST request to save CollisionLevelThree : {}", collisionLevelThreeDTO);
        if (collisionLevelThreeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new collisionLevelThree cannot already have an ID")).body(null);
        }
        CollisionLevelThreeDTO result = collisionLevelThreeService.save(collisionLevelThreeDTO);
        return ResponseEntity.created(new URI("/api/collision-level-threes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collision-level-threes : Updates an existing collisionLevelThree.
     *
     * @param collisionLevelThreeDTO the collisionLevelThreeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collisionLevelThreeDTO,
     * or with status 400 (Bad Request) if the collisionLevelThreeDTO is not valid,
     * or with status 500 (Internal Server Error) if the collisionLevelThreeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collision-level-threes")
    @Timed
    public ResponseEntity<CollisionLevelThreeDTO> updateCollisionLevelThree(@RequestBody CollisionLevelThreeDTO collisionLevelThreeDTO) throws URISyntaxException {
        log.debug("REST request to update CollisionLevelThree : {}", collisionLevelThreeDTO);
        if (collisionLevelThreeDTO.getId() == null) {
            return createCollisionLevelThree(collisionLevelThreeDTO);
        }
        CollisionLevelThreeDTO result = collisionLevelThreeService.save(collisionLevelThreeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collisionLevelThreeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collision-level-threes : get all the collisionLevelThrees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collisionLevelThrees in body
     */
    @GetMapping("/collision-level-threes")
    @Timed
    public List<CollisionLevelThreeDTO> getAllCollisionLevelThrees() {
        log.debug("REST request to get all CollisionLevelThrees");
        return collisionLevelThreeService.findAll();
    }

    /**
     * GET  /collision-level-threes : get all the collisionLevelThrees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collisionLevelThrees in body
     */
    @GetMapping("/collision-level-threes/filtered/{id}")
    @Timed
    public List<CollisionLevelThreeDTO> findByCollisionLevelTwo_Id(@PathVariable Long id) {
        log.debug("REST request to get all CollisionLevelThrees");
        List<CollisionLevelThreeDTO> byCollisionLevelTwo_id = collisionLevelThreeService.findByCollisionLevelTwo_Id(id);
        return byCollisionLevelTwo_id;
    }

    /**
     * GET  /collision-level-threes/:id : get the "id" collisionLevelThree.
     *
     * @param id the id of the collisionLevelThreeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collisionLevelThreeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/collision-level-threes/{id}")
    @Timed
    public ResponseEntity<CollisionLevelThreeDTO> getCollisionLevelThree(@PathVariable Long id) {
        log.debug("REST request to get CollisionLevelThree : {}", id);
        CollisionLevelThreeDTO collisionLevelThreeDTO = collisionLevelThreeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collisionLevelThreeDTO));
    }

    /**
     * DELETE  /collision-level-threes/:id : delete the "id" collisionLevelThree.
     *
     * @param id the id of the collisionLevelThreeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collision-level-threes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollisionLevelThree(@PathVariable Long id) {
        log.debug("REST request to delete CollisionLevelThree : {}", id);
        collisionLevelThreeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/collision-level-threes?query=:query : search for the collisionLevelThree corresponding
     * to the query.
     *
     * @param query the query of the collisionLevelThree search
     * @return the result of the search
     */
    @GetMapping("/_search/collision-level-threes")
    @Timed
    public List<CollisionLevelThreeDTO> searchCollisionLevelThrees(@RequestParam String query) {
        log.debug("REST request to search CollisionLevelThrees for query {}", query);
        return collisionLevelThreeService.search(query);
    }

}
