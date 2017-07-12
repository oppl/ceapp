package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.CollisionSummaryCSService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.CollisionSummaryCSDTO;
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
 * REST controller for managing CollisionSummaryCS.
 */
@RestController
@RequestMapping("/api")
public class CollisionSummaryCSResource {

    private final Logger log = LoggerFactory.getLogger(CollisionSummaryCSResource.class);

    private static final String ENTITY_NAME = "collisionSummaryCS";

    private final CollisionSummaryCSService collisionSummaryCSService;

    public CollisionSummaryCSResource(CollisionSummaryCSService collisionSummaryCSService) {
        this.collisionSummaryCSService = collisionSummaryCSService;
    }

    /**
     * POST  /collision-summary-cs : Create a new collisionSummaryCS.
     *
     * @param collisionSummaryCSDTO the collisionSummaryCSDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collisionSummaryCSDTO, or with status 400 (Bad Request) if the collisionSummaryCS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collision-summary-cs")
    @Timed
    public ResponseEntity<CollisionSummaryCSDTO> createCollisionSummaryCS(@RequestBody CollisionSummaryCSDTO collisionSummaryCSDTO) throws URISyntaxException {
        log.debug("REST request to save CollisionSummaryCS : {}", collisionSummaryCSDTO);
        if (collisionSummaryCSDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new collisionSummaryCS cannot already have an ID")).body(null);
        }
        CollisionSummaryCSDTO result = collisionSummaryCSService.save(collisionSummaryCSDTO);
        return ResponseEntity.created(new URI("/api/collision-summary-cs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collision-summary-cs : Updates an existing collisionSummaryCS.
     *
     * @param collisionSummaryCSDTO the collisionSummaryCSDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collisionSummaryCSDTO,
     * or with status 400 (Bad Request) if the collisionSummaryCSDTO is not valid,
     * or with status 500 (Internal Server Error) if the collisionSummaryCSDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collision-summary-cs")
    @Timed
    public ResponseEntity<CollisionSummaryCSDTO> updateCollisionSummaryCS(@RequestBody CollisionSummaryCSDTO collisionSummaryCSDTO) throws URISyntaxException {
        log.debug("REST request to update CollisionSummaryCS : {}", collisionSummaryCSDTO);
        if (collisionSummaryCSDTO.getId() == null) {
            return createCollisionSummaryCS(collisionSummaryCSDTO);
        }
        CollisionSummaryCSDTO result = collisionSummaryCSService.save(collisionSummaryCSDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collisionSummaryCSDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collision-summary-cs : get all the collisionSummaryCS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collisionSummaryCS in body
     */
    @GetMapping("/collision-summary-cs")
    @Timed
    public List<CollisionSummaryCSDTO> getAllCollisionSummaryCS() {
        log.debug("REST request to get all CollisionSummaryCS");
        return collisionSummaryCSService.findAll();
    }

    /**
     * GET  /collision-summary-cs/:id : get the "id" collisionSummaryCS.
     *
     * @param id the id of the collisionSummaryCSDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collisionSummaryCSDTO, or with status 404 (Not Found)
     */
    @GetMapping("/collision-summary-cs/{id}")
    @Timed
    public ResponseEntity<CollisionSummaryCSDTO> getCollisionSummaryCS(@PathVariable Long id) {
        log.debug("REST request to get CollisionSummaryCS : {}", id);
        CollisionSummaryCSDTO collisionSummaryCSDTO = collisionSummaryCSService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collisionSummaryCSDTO));
    }

    /**
     * DELETE  /collision-summary-cs/:id : delete the "id" collisionSummaryCS.
     *
     * @param id the id of the collisionSummaryCSDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collision-summary-cs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollisionSummaryCS(@PathVariable Long id) {
        log.debug("REST request to delete CollisionSummaryCS : {}", id);
        collisionSummaryCSService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/collision-summary-cs?query=:query : search for the collisionSummaryCS corresponding
     * to the query.
     *
     * @param query the query of the collisionSummaryCS search
     * @return the result of the search
     */
    @GetMapping("/_search/collision-summary-cs")
    @Timed
    public List<CollisionSummaryCSDTO> searchCollisionSummaryCS(@RequestParam String query) {
        log.debug("REST request to search CollisionSummaryCS for query {}", query);
        return collisionSummaryCSService.search(query);
    }

}
