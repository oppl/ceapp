package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.CollisionLevelTwoService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.CollisionLevelTwoDTO;
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
 * REST controller for managing CollisionLevelTwo.
 */
@RestController
@RequestMapping("/api")
public class CollisionLevelTwoResource {

    private final Logger log = LoggerFactory.getLogger(CollisionLevelTwoResource.class);

    private static final String ENTITY_NAME = "collisionLevelTwo";

    private final CollisionLevelTwoService collisionLevelTwoService;

    public CollisionLevelTwoResource(CollisionLevelTwoService collisionLevelTwoService) {
        this.collisionLevelTwoService = collisionLevelTwoService;
    }

    /**
     * POST  /collision-level-twos : Create a new collisionLevelTwo.
     *
     * @param collisionLevelTwoDTO the collisionLevelTwoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collisionLevelTwoDTO, or with status 400 (Bad Request) if the collisionLevelTwo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collision-level-twos")
    @Timed
    public ResponseEntity<CollisionLevelTwoDTO> createCollisionLevelTwo(@RequestBody CollisionLevelTwoDTO collisionLevelTwoDTO) throws URISyntaxException {
        log.debug("REST request to save CollisionLevelTwo : {}", collisionLevelTwoDTO);
        if (collisionLevelTwoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new collisionLevelTwo cannot already have an ID")).body(null);
        }
        CollisionLevelTwoDTO result = collisionLevelTwoService.save(collisionLevelTwoDTO);
        return ResponseEntity.created(new URI("/api/collision-level-twos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collision-level-twos : Updates an existing collisionLevelTwo.
     *
     * @param collisionLevelTwoDTO the collisionLevelTwoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collisionLevelTwoDTO,
     * or with status 400 (Bad Request) if the collisionLevelTwoDTO is not valid,
     * or with status 500 (Internal Server Error) if the collisionLevelTwoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collision-level-twos")
    @Timed
    public ResponseEntity<CollisionLevelTwoDTO> updateCollisionLevelTwo(@RequestBody CollisionLevelTwoDTO collisionLevelTwoDTO) throws URISyntaxException {
        log.debug("REST request to update CollisionLevelTwo : {}", collisionLevelTwoDTO);
        if (collisionLevelTwoDTO.getId() == null) {
            return createCollisionLevelTwo(collisionLevelTwoDTO);
        }
        CollisionLevelTwoDTO result = collisionLevelTwoService.save(collisionLevelTwoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collisionLevelTwoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collision-level-twos : get all the collisionLevelTwos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collisionLevelTwos in body
     */
    @GetMapping("/collision-level-twos")
    @Timed
    public List<CollisionLevelTwoDTO> getAllCollisionLevelTwos() {
        log.debug("REST request to get all CollisionLevelTwos");
        return collisionLevelTwoService.findAll();
    }

    /**
     * GET  /collision-level-twos : get all the collisionLevelTwos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collisionLevelTwos in body
     */
    @GetMapping("/collision-level-twos/filtered/{id}")
    @Timed
    public List<CollisionLevelTwoDTO> findByCollisionLevelOne_Id(@PathVariable Long id) {
        log.debug("REST request to get all CollisionLevelTwos");
        List<CollisionLevelTwoDTO> byCollisionLevelOne_id = collisionLevelTwoService.findByCollisionLevelOne_Id(id);
        return byCollisionLevelOne_id;
    }

    /**
     * GET  /collision-level-twos/:id : get the "id" collisionLevelTwo.
     *
     * @param id the id of the collisionLevelTwoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collisionLevelTwoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/collision-level-twos/{id}")
    @Timed
    public ResponseEntity<CollisionLevelTwoDTO> getCollisionLevelTwo(@PathVariable Long id) {
        log.debug("REST request to get CollisionLevelTwo : {}", id);
        CollisionLevelTwoDTO collisionLevelTwoDTO = collisionLevelTwoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collisionLevelTwoDTO));
    }

    /**
     * DELETE  /collision-level-twos/:id : delete the "id" collisionLevelTwo.
     *
     * @param id the id of the collisionLevelTwoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collision-level-twos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollisionLevelTwo(@PathVariable Long id) {
        log.debug("REST request to delete CollisionLevelTwo : {}", id);
        collisionLevelTwoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/collision-level-twos?query=:query : search for the collisionLevelTwo corresponding
     * to the query.
     *
     * @param query the query of the collisionLevelTwo search
     * @return the result of the search
     */
    @GetMapping("/_search/collision-level-twos")
    @Timed
    public List<CollisionLevelTwoDTO> searchCollisionLevelTwos(@RequestParam String query) {
        log.debug("REST request to search CollisionLevelTwos for query {}", query);
        return collisionLevelTwoService.search(query);
    }

}
