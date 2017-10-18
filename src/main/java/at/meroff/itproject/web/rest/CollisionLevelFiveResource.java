package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.CollisionLevelFiveService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.CollisionLevelFiveDTO;
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
 * REST controller for managing CollisionLevelFive.
 */
@RestController
@RequestMapping("/api")
public class CollisionLevelFiveResource {

    private final Logger log = LoggerFactory.getLogger(CollisionLevelFiveResource.class);

    private static final String ENTITY_NAME = "collisionLevelFive";

    private final CollisionLevelFiveService collisionLevelFiveService;

    public CollisionLevelFiveResource(CollisionLevelFiveService collisionLevelFiveService) {
        this.collisionLevelFiveService = collisionLevelFiveService;
    }

    /**
     * POST  /collision-level-fives : Create a new collisionLevelFive.
     *
     * @param collisionLevelFiveDTO the collisionLevelFiveDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collisionLevelFiveDTO, or with status 400 (Bad Request) if the collisionLevelFive has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collision-level-fives")
    @Timed
    public ResponseEntity<CollisionLevelFiveDTO> createCollisionLevelFive(@RequestBody CollisionLevelFiveDTO collisionLevelFiveDTO) throws URISyntaxException {
        log.debug("REST request to save CollisionLevelFive : {}", collisionLevelFiveDTO);
        if (collisionLevelFiveDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new collisionLevelFive cannot already have an ID")).body(null);
        }
        CollisionLevelFiveDTO result = collisionLevelFiveService.save(collisionLevelFiveDTO);
        return ResponseEntity.created(new URI("/api/collision-level-fives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collision-level-fives : Updates an existing collisionLevelFive.
     *
     * @param collisionLevelFiveDTO the collisionLevelFiveDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collisionLevelFiveDTO,
     * or with status 400 (Bad Request) if the collisionLevelFiveDTO is not valid,
     * or with status 500 (Internal Server Error) if the collisionLevelFiveDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collision-level-fives")
    @Timed
    public ResponseEntity<CollisionLevelFiveDTO> updateCollisionLevelFive(@RequestBody CollisionLevelFiveDTO collisionLevelFiveDTO) throws URISyntaxException {
        log.debug("REST request to update CollisionLevelFive : {}", collisionLevelFiveDTO);
        if (collisionLevelFiveDTO.getId() == null) {
            return createCollisionLevelFive(collisionLevelFiveDTO);
        }
        CollisionLevelFiveDTO result = collisionLevelFiveService.save(collisionLevelFiveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collisionLevelFiveDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collision-level-fives : get all the collisionLevelFives.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collisionLevelFives in body
     */
    @GetMapping("/collision-level-fives")
    @Timed
    public List<CollisionLevelFiveDTO> getAllCollisionLevelFives() {
        log.debug("REST request to get all CollisionLevelFives");
        return collisionLevelFiveService.findAll();
    }

    /**
     * GET  /collision-level-fives : get all the collisionLevelFives.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collisionLevelFives in body
     */
    @GetMapping("/collision-level-fives/filtered/{id}")
    @Timed
    public List<CollisionLevelFiveDTO> findByCollisionLevelFour_Id(@PathVariable Long id) {
        log.debug("REST request to get all CollisionLevelFives");
        List<CollisionLevelFiveDTO> byCollisionLevelFour_id = collisionLevelFiveService.findByCollisionLevelFour_Id(id);
        return byCollisionLevelFour_id;
    }

    /**
     * GET  /collision-level-fives/:id : get the "id" collisionLevelFive.
     *
     * @param id the id of the collisionLevelFiveDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collisionLevelFiveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/collision-level-fives/{id}")
    @Timed
    public ResponseEntity<CollisionLevelFiveDTO> getCollisionLevelFive(@PathVariable Long id) {
        log.debug("REST request to get CollisionLevelFive : {}", id);
        CollisionLevelFiveDTO collisionLevelFiveDTO = collisionLevelFiveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collisionLevelFiveDTO));
    }

    /**
     * DELETE  /collision-level-fives/:id : delete the "id" collisionLevelFive.
     *
     * @param id the id of the collisionLevelFiveDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collision-level-fives/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollisionLevelFive(@PathVariable Long id) {
        log.debug("REST request to delete CollisionLevelFive : {}", id);
        collisionLevelFiveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/collision-level-fives?query=:query : search for the collisionLevelFive corresponding
     * to the query.
     *
     * @param query the query of the collisionLevelFive search
     * @return the result of the search
     */
    @GetMapping("/_search/collision-level-fives")
    @Timed
    public List<CollisionLevelFiveDTO> searchCollisionLevelFives(@RequestParam String query) {
        log.debug("REST request to search CollisionLevelFives for query {}", query);
        return collisionLevelFiveService.search(query);
    }

}
