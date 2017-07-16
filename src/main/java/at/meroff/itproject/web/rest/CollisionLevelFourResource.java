package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.CollisionLevelFourService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.CollisionLevelFourDTO;
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
 * REST controller for managing CollisionLevelFour.
 */
@RestController
@RequestMapping("/api")
public class CollisionLevelFourResource {

    private final Logger log = LoggerFactory.getLogger(CollisionLevelFourResource.class);

    private static final String ENTITY_NAME = "collisionLevelFour";

    private final CollisionLevelFourService collisionLevelFourService;

    public CollisionLevelFourResource(CollisionLevelFourService collisionLevelFourService) {
        this.collisionLevelFourService = collisionLevelFourService;
    }

    /**
     * POST  /collision-level-fours : Create a new collisionLevelFour.
     *
     * @param collisionLevelFourDTO the collisionLevelFourDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collisionLevelFourDTO, or with status 400 (Bad Request) if the collisionLevelFour has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collision-level-fours")
    @Timed
    public ResponseEntity<CollisionLevelFourDTO> createCollisionLevelFour(@RequestBody CollisionLevelFourDTO collisionLevelFourDTO) throws URISyntaxException {
        log.debug("REST request to save CollisionLevelFour : {}", collisionLevelFourDTO);
        if (collisionLevelFourDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new collisionLevelFour cannot already have an ID")).body(null);
        }
        CollisionLevelFourDTO result = collisionLevelFourService.save(collisionLevelFourDTO);
        return ResponseEntity.created(new URI("/api/collision-level-fours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collision-level-fours : Updates an existing collisionLevelFour.
     *
     * @param collisionLevelFourDTO the collisionLevelFourDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collisionLevelFourDTO,
     * or with status 400 (Bad Request) if the collisionLevelFourDTO is not valid,
     * or with status 500 (Internal Server Error) if the collisionLevelFourDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collision-level-fours")
    @Timed
    public ResponseEntity<CollisionLevelFourDTO> updateCollisionLevelFour(@RequestBody CollisionLevelFourDTO collisionLevelFourDTO) throws URISyntaxException {
        log.debug("REST request to update CollisionLevelFour : {}", collisionLevelFourDTO);
        if (collisionLevelFourDTO.getId() == null) {
            return createCollisionLevelFour(collisionLevelFourDTO);
        }
        CollisionLevelFourDTO result = collisionLevelFourService.save(collisionLevelFourDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collisionLevelFourDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collision-level-fours : get all the collisionLevelFours.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collisionLevelFours in body
     */
    @GetMapping("/collision-level-fours")
    @Timed
    public List<CollisionLevelFourDTO> getAllCollisionLevelFours() {
        log.debug("REST request to get all CollisionLevelFours");
        return collisionLevelFourService.findAll();
    }

    /**
     * GET  /collision-level-fours/:id : get the "id" collisionLevelFour.
     *
     * @param id the id of the collisionLevelFourDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collisionLevelFourDTO, or with status 404 (Not Found)
     */
    @GetMapping("/collision-level-fours/{id}")
    @Timed
    public ResponseEntity<CollisionLevelFourDTO> getCollisionLevelFour(@PathVariable Long id) {
        log.debug("REST request to get CollisionLevelFour : {}", id);
        CollisionLevelFourDTO collisionLevelFourDTO = collisionLevelFourService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collisionLevelFourDTO));
    }

    /**
     * DELETE  /collision-level-fours/:id : delete the "id" collisionLevelFour.
     *
     * @param id the id of the collisionLevelFourDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collision-level-fours/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollisionLevelFour(@PathVariable Long id) {
        log.debug("REST request to delete CollisionLevelFour : {}", id);
        collisionLevelFourService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/collision-level-fours?query=:query : search for the collisionLevelFour corresponding
     * to the query.
     *
     * @param query the query of the collisionLevelFour search
     * @return the result of the search
     */
    @GetMapping("/_search/collision-level-fours")
    @Timed
    public List<CollisionLevelFourDTO> searchCollisionLevelFours(@RequestParam String query) {
        log.debug("REST request to search CollisionLevelFours for query {}", query);
        return collisionLevelFourService.search(query);
    }

}
