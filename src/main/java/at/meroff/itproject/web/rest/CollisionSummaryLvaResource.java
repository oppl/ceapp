package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.CollisionSummaryLvaService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.CollisionSummaryLvaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CollisionSummaryLva.
 */
@RestController
@RequestMapping("/api")
public class CollisionSummaryLvaResource {

    private final Logger log = LoggerFactory.getLogger(CollisionSummaryLvaResource.class);

    private static final String ENTITY_NAME = "collisionSummaryLva";

    private final CollisionSummaryLvaService collisionSummaryLvaService;

    public CollisionSummaryLvaResource(CollisionSummaryLvaService collisionSummaryLvaService) {
        this.collisionSummaryLvaService = collisionSummaryLvaService;
    }

    /**
     * POST  /collision-summary-lvas : Create a new collisionSummaryLva.
     *
     * @param collisionSummaryLvaDTO the collisionSummaryLvaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collisionSummaryLvaDTO, or with status 400 (Bad Request) if the collisionSummaryLva has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collision-summary-lvas")
    @Timed
    public ResponseEntity<CollisionSummaryLvaDTO> createCollisionSummaryLva(@RequestBody CollisionSummaryLvaDTO collisionSummaryLvaDTO) throws URISyntaxException {
        log.debug("REST request to save CollisionSummaryLva : {}", collisionSummaryLvaDTO);
        if (collisionSummaryLvaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new collisionSummaryLva cannot already have an ID")).body(null);
        }
        CollisionSummaryLvaDTO result = collisionSummaryLvaService.save(collisionSummaryLvaDTO);
        return ResponseEntity.created(new URI("/api/collision-summary-lvas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collision-summary-lvas : Updates an existing collisionSummaryLva.
     *
     * @param collisionSummaryLvaDTO the collisionSummaryLvaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collisionSummaryLvaDTO,
     * or with status 400 (Bad Request) if the collisionSummaryLvaDTO is not valid,
     * or with status 500 (Internal Server Error) if the collisionSummaryLvaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collision-summary-lvas")
    @Timed
    public ResponseEntity<CollisionSummaryLvaDTO> updateCollisionSummaryLva(@RequestBody CollisionSummaryLvaDTO collisionSummaryLvaDTO) throws URISyntaxException {
        log.debug("REST request to update CollisionSummaryLva : {}", collisionSummaryLvaDTO);
        if (collisionSummaryLvaDTO.getId() == null) {
            return createCollisionSummaryLva(collisionSummaryLvaDTO);
        }
        CollisionSummaryLvaDTO result = collisionSummaryLvaService.save(collisionSummaryLvaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collisionSummaryLvaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collision-summary-lvas : get all the collisionSummaryLvas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collisionSummaryLvas in body
     */
    @GetMapping("/collision-summary-lvas")
    @Timed
    public List<CollisionSummaryLvaDTO> getAllCollisionSummaryLvas() {
        log.debug("REST request to get all CollisionSummaryLvas");
        return collisionSummaryLvaService.findAll();
    }

    /**
     * GET  /collision-summary-lvas/:id : get the "id" collisionSummaryLva.
     *
     * @param id the id of the collisionSummaryLvaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collisionSummaryLvaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/collision-summary-lvas/{id}")
    @Timed
    public ResponseEntity<CollisionSummaryLvaDTO> getCollisionSummaryLva(@PathVariable Long id) {
        log.debug("REST request to get CollisionSummaryLva : {}", id);
        CollisionSummaryLvaDTO collisionSummaryLvaDTO = collisionSummaryLvaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collisionSummaryLvaDTO));
    }

    /**
     * DELETE  /collision-summary-lvas/:id : delete the "id" collisionSummaryLva.
     *
     * @param id the id of the collisionSummaryLvaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collision-summary-lvas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollisionSummaryLva(@PathVariable Long id) {
        log.debug("REST request to delete CollisionSummaryLva : {}", id);
        collisionSummaryLvaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
