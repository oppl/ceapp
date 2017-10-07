package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.CollisionLevelOneService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.CollisionLevelOneDTO;
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
 * REST controller for managing CollisionLevelOne.
 */
@RestController
@RequestMapping("/api")
public class CollisionLevelOneResource {

    private final Logger log = LoggerFactory.getLogger(CollisionLevelOneResource.class);

    private static final String ENTITY_NAME = "collisionLevelOne";

    private final CollisionLevelOneService collisionLevelOneService;

    public CollisionLevelOneResource(CollisionLevelOneService collisionLevelOneService) {
        this.collisionLevelOneService = collisionLevelOneService;
    }

    /**
     * POST  /collision-level-ones : Create a new collisionLevelOne.
     *
     * @param collisionLevelOneDTO the collisionLevelOneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collisionLevelOneDTO, or with status 400 (Bad Request) if the collisionLevelOne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collision-level-ones")
    @Timed
    public ResponseEntity<CollisionLevelOneDTO> createCollisionLevelOne(@RequestBody CollisionLevelOneDTO collisionLevelOneDTO) throws URISyntaxException {
        log.debug("REST request to save CollisionLevelOne : {}", collisionLevelOneDTO);
        if (collisionLevelOneDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new collisionLevelOne cannot already have an ID")).body(null);
        }
        CollisionLevelOneDTO result = collisionLevelOneService.save(collisionLevelOneDTO);
        return ResponseEntity.created(new URI("/api/collision-level-ones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collision-level-ones : Updates an existing collisionLevelOne.
     *
     * @param collisionLevelOneDTO the collisionLevelOneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collisionLevelOneDTO,
     * or with status 400 (Bad Request) if the collisionLevelOneDTO is not valid,
     * or with status 500 (Internal Server Error) if the collisionLevelOneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collision-level-ones")
    @Timed
    public ResponseEntity<CollisionLevelOneDTO> updateCollisionLevelOne(@RequestBody CollisionLevelOneDTO collisionLevelOneDTO) throws URISyntaxException {
        log.debug("REST request to update CollisionLevelOne : {}", collisionLevelOneDTO);
        if (collisionLevelOneDTO.getId() == null) {
            return createCollisionLevelOne(collisionLevelOneDTO);
        }
        CollisionLevelOneDTO result = collisionLevelOneService.save(collisionLevelOneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collisionLevelOneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collision-level-ones : get all the collisionLevelOnes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collisionLevelOnes in body
     */
    @GetMapping("/collision-level-ones")
    @Timed
    public List<CollisionLevelOneDTO> getAllCollisionLevelOnes() {
        log.debug("REST request to get all CollisionLevelOnes");
        return collisionLevelOneService.findAll();
    }

    /**
     * GET  /collision-level-ones : get all the collisionLevelOnes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collisionLevelOnes in body
     */
    @GetMapping("/collision-level-ones/{cs}/{ip}")
    @Timed
    public List<CollisionLevelOneDTO> getAllCollisionLevelOnes2(@PathVariable Long cs, @PathVariable Long ip) {
        log.debug("REST request to get all CollisionLevelOnes");
        return collisionLevelOneService.findByCsIp(cs, ip);
    }


    /**
     * GET  /collision-level-ones/:id : get the "id" collisionLevelOne.
     *
     * @param id the id of the collisionLevelOneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collisionLevelOneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/collision-level-ones/{id}")
    @Timed
    public ResponseEntity<CollisionLevelOneDTO> getCollisionLevelOne(@PathVariable Long id) {
        log.debug("REST request to get CollisionLevelOne : {}", id);
        CollisionLevelOneDTO collisionLevelOneDTO = collisionLevelOneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collisionLevelOneDTO));
    }

    /**
     * DELETE  /collision-level-ones/:id : delete the "id" collisionLevelOne.
     *
     * @param id the id of the collisionLevelOneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collision-level-ones/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollisionLevelOne(@PathVariable Long id) {
        log.debug("REST request to delete CollisionLevelOne : {}", id);
        collisionLevelOneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/collision-level-ones?query=:query : search for the collisionLevelOne corresponding
     * to the query.
     *
     * @param query the query of the collisionLevelOne search
     * @return the result of the search
     */
    @GetMapping("/_search/collision-level-ones")
    @Timed
    public List<CollisionLevelOneDTO> searchCollisionLevelOnes(@RequestParam String query) {
        log.debug("REST request to search CollisionLevelOnes for query {}", query);
        return collisionLevelOneService.search(query);
    }

}
